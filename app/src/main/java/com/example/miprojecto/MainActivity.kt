package com.example.miprojecto

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.media.MediaPlayer
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build.VERSION_CODES.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.miprojecto.adapter.SuperHeroAdapter
import com.example.miprojecto.databinding.ActivityMainBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.chip.Chip
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream
import java.lang.Thread.sleep
import java.math.RoundingMode
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*


class MainActivity : AppCompatActivity() {

    private val fechaActual: LocalDate = LocalDate.now()
    private val fechaOriginal: String = SaveDatos.prefs.getDia()
    private val dinerSave: String = SaveDatos.prefs.getAcumuladorString()
    private var bitmap: Bitmap? = null

    private lateinit var mpSoundBoton: MediaPlayer

    lateinit var binding: ActivityMainBinding

    private var superHeroMutableList: MutableList<SuperHero> =
        SuperHeroProvider.superHeroList.toMutableList()
    private lateinit var adapter: SuperHeroAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAds()
        initSound()
        //menu
        menuNavigation()
        millisegundos()
        newSuperHero()
        circleBar()
        limiteporDia()
        chips()
        initRecyclerView()
        binding.edFilter.addTextChangedListener { userFilter ->
            val movesFilter = superHeroMutableList.filter { movimientos ->
                movimientos.categoria.lowercase().contains(
                    userFilter.toString().lowercase()
                )
            }
            adapter.updateMovesFilter(movesFilter)
        }

        //muestra el dinero que va       ANDA BIEN
        binding.tvTotalDinero.text.apply {
            val observador = dinerSave
            val a = formatImporte(observador.toDouble())
            binding.tvTotalDinero.text = a
        }

        //texview que muestra cuantos dias faltan     ANDA BIEN NO TOCAR
        binding.tvFaltanDias.text.apply {
            val fecha_elegida = fechaOriginal
            val fechaDespues = LocalDate.parse(fecha_elegida)
            if (fechaActual.isBefore(fechaDespues)) {
                binding.tvFaltanDias.text = dia(fecha_elegida).toString()
            } else if (fechaActual.isAfter(fechaDespues)) {
                val newMes = mesAdd(fechaDespues)
                binding.tvFaltanDias.text = dia(newMes).toString()
            }
        }


        //fecha muestra descuento          ANDA BIEN NO TOCAR
        binding.tvContadorFecha.text.apply {
            val fechaElegida = fechaOriginal
            val fechaParseada = LocalDate.parse(fechaElegida)
            val fechaFormateada = formatFecha(fechaParseada)
            binding.tvContadorFecha.text = "Descuento $fechaFormateada"
            if (fechaActual.isAfter(fechaParseada)) {
                val newMes = mesAdd(fechaParseada)
                val newFecha = LocalDate.parse(newMes)
                val nuevaFecha = formatFecha(newFecha)
                binding.tvContadorFecha.text = "Descuento $nuevaFecha"
            }
        }


    }

    private fun chips() {
        var chip: Chip
        for (i in 0 until binding.chipGroup.childCount) {
            chip = binding.chipGroup.getChildAt(i) as Chip
            chip.textAlignment = View.TEXT_ALIGNMENT_CENTER
            chip.setOnClickListener {
                val aux = it as Chip
                onClick(aux)
            }
        }

    }


    private fun initSound() {
        mpSoundBoton = MediaPlayer.create(this, R.raw.sound1)
        mpSoundBoton.isLooping = false
    }

    private fun limiteporDia() {
        // ANDA BIEN NO TOCAR
        binding.tvLimiteDia.text.apply {
            var dinero = dinerSave
            val fecha_elegida = fechaOriginal
            val fDespues = LocalDate.parse(fecha_elegida)
            if (fechaActual.isBefore(fDespues)) {
                var num: Long = ChronoUnit.DAYS.between(fechaActual, fDespues)
                num += 1
                val result =
                    dinero.toBigDecimal().divide(num.toBigDecimal(), 2, RoundingMode.HALF_EVEN)
                val a = formatImporte(result.toDouble())
                binding.tvLimiteDia.text = a
            } else if (fechaActual.isEqual(fDespues)) {
                dinero = formatImporte(dinero.toDouble())
                binding.tvLimiteDia.text = dinero

            }
        }
    }


    @OptIn(DelicateCoroutinesApi::class)
    private fun circleBar() {
        //getDia es la fecha original 0000-00-00
        val fecha_elegida = fechaOriginal
        val fechaDespues = LocalDate.parse(fecha_elegida)
        if (fechaActual <= fechaDespues) {
            binding.circlePbar.max =
                dia(fechaDespues.toString()).toInt().plus(fechaActual.dayOfMonth).minus(1)
            SaveDatos.prefs.saveCountCircleString(binding.circlePbar.max.toString())
            val maxContador: String = SaveDatos.prefs.getCountCircleString()
            binding.circlePbar.min = 0
            binding.circlePbar.max = maxContador.toInt()

            binding.circlePbar.progress = fechaActual.dayOfMonth
            GlobalScope.launch { corrutinaCircle(binding.circlePbar) }
            binding.tvProgress.text = "progrees = ${binding.circlePbar.progress}"
            binding.tvMax.text = "max = ${binding.circlePbar.max}"
            GlobalScope.launch {
                corrutinaCircle(binding.circlePbar)
                millisegundos()
            }

        } else if (fechaActual.isAfter(fechaDespues)) {
            val nuevaFecha = mesAdd(fechaDespues)
            binding.circlePbar.min = 0
            binding.circlePbar.max = dia(nuevaFecha).toInt().plus(fechaActual.dayOfMonth).minus(1)
            GlobalScope.launch { corrutinaCircle(binding.circlePbar) }
            binding.tvMax.text = "max = ${binding.circlePbar.max}"

        } else {
            binding.circlePbar.min = 0
            binding.circlePbar.max =
                dia(fechaDespues.toString()).toInt().plus(fechaActual.dayOfMonth).minus(1)
            // progress = fechaActual.dayOfMonth
            GlobalScope.launch {
                corrutinaCircle(binding.circlePbar)
                millisegundos()
            }
        }

    }

    private fun corrutinaCircle(pb: ProgressBar) {
        val diaActual = fechaActual.dayOfMonth

        while (pb.progress <= pb.max || pb.progress > pb.max) {

            if (pb.progress > pb.max) {
                pb.progress = 0
                //guardar dato
                break
            } else if (diaActual == fechaActual.dayOfMonth) {
                sleep(1000)
                pb.incrementProgressBy(1)
                //guardar dato
                // SaveDatos.prefs.saveStringDiaActualCircleBar(countDia)
            }


        }
    }

    private fun millisegundos(): String {
        val c = Calendar.getInstance()
        var diaSiguiente = c.add(Calendar.DAY_OF_MONTH, 1)
        var hours = c.set(Calendar.HOUR_OF_DAY, 0)
        var minutes = c.set(Calendar.MINUTE, 0)
        var seconds = c.set(Calendar.SECOND, 0)
        var milliseconds = c.set(Calendar.SECOND, 0)

        val horaActual = System.currentTimeMillis()
        // Obtenemos los milisegundos de la proxima medianoche
        val proxNigth = c.timeInMillis

        // Cuandos milisegundos har de diferencia
        val milisegundos = proxNigth - horaActual
        println("MiliSegundos para media noche:  $milisegundos")

        // Calculamos las horas
        val horas = milisegundos / (1000 * 60 * 60);
        println("Horas medianoche: $horas")

// Obtenemos el modulo que sean los minutos
        val minutosMili = milisegundos % (1000 * 60 * 60);
// Calculamos los minutos
        val minutos = minutosMili / (1000 * 60);
        println("Minutos medianoche: $minutos")

// Obtenemos el modulo de los minutos
        val segundosMili = minutosMili % (1000 * 60)
// Calculamos los segundos
        val segundos = segundosMili / 1000

        println("Segundos medianoche: $segundos")

// Presentamos los datos

        binding.tempo.text = "$horas:$minutos:$segundos   y milisegundos $milisegundos"
        val tempo = binding.tempo.text
        return tempo.toString()
    }


    private fun initRecyclerView() {
        adapter = SuperHeroAdapter(
            superHeroList = superHeroMutableList,
            onClickListener = { superhero -> onItemSelected(superhero) },
            onClickDeleted = { position -> onDeletedItem(position) }
        )

        val manager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(this, manager.orientation)
        binding.recyclerSuperHero.layoutManager = manager
        binding.recyclerSuperHero.adapter = adapter

        // binding.recyclerSuperHero.addItemDecoration(decoration)
    }

    private fun onItemSelected(superHero: SuperHero) {
        Toast.makeText(this, superHero.categoria, Toast.LENGTH_SHORT).show()
    }

    private fun onDeletedItem(position: Int) {
        superHeroMutableList.removeAt(position)
        adapter.notifyItemRemoved(position)
    }

    private fun newSuperHero() {
        val superHero =
            SuperHero(
                "Carrefour",
                "colchon",
                R.drawable.borde,
                "$25,231.30",
                formatFecha(fechaActual)
            )
        superHeroMutableList.add(index = 0, superHero)
        adapter.notifyItemInserted(0)
    }


    private fun initAds() {
        MobileAds.initialize(this) {}
        val adView = AdView(this)

        adView.setAdSize(AdSize.BANNER)
        adView.adUnitId = "ca-app-pub-6530744878695408/4702008861"

        val publicidad = findViewById<LinearLayout>(R.id.publicidad)
        publicidad.addView(adView)

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    fun onClick(view: View) {
        when (view.id) {

            // R.id.btn_editar -> startActivity(Intent(this, ActivityCuentas::class.java))
            R.id.floatingActionButton -> startActivity(Intent(this, ActivityDinero::class.java))
            R.id.circlePbar -> startActivity(Intent(this, ActivityFecha::class.java))
            R.id.share -> share()
            R.id.chip1 -> startActivity(Intent(this, ActivityCuentas::class.java))
            R.id.chip2 -> startActivity(Intent(this, InversionCalculadora::class.java))
            R.id.chip3 -> Toast.makeText(this, "chip3", Toast.LENGTH_SHORT).show()
            R.id.chip4 -> Toast.makeText(this, "chip4", Toast.LENGTH_SHORT).show()

        }
    }

    private fun formatImporte(num: Double?): String {
        val formatoImporte: NumberFormat = NumberFormat.getCurrencyInstance()
        return formatoImporte.format(num)
        //Si se desea forzar el formato español:
        /*val formatoImporte = NumberFormat.getCurrencyInstance(Locale("es","ES"))
         return formatoImporte.format(num)*/
    }

    //dia obtiene la fecha 0000-00-00
    private fun dia(fDespues: String?): Long {
        val fechaDespues = LocalDate.parse(fDespues)
        var dia = ChronoUnit.DAYS.between(fechaActual, fechaDespues)
        dia += 1
        return dia
    }

    private fun formatFecha(a: LocalDate): String {
        val format = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        return a.format(format)
    }

    private fun mesAdd(fDespues: LocalDate): String {
        var diff = ChronoUnit.MONTHS.between(fDespues, fechaActual)
        diff += 1
        val mes = fDespues.plus(diff, ChronoUnit.MONTHS)
        return mes.toString()
    }

/* private fun share(){
     var bitmap:Bitmap?

     val captura:View = window.decorView.rootView
     var ssc: Bitmap = Bitmap.createBitmap(captura.width, captura.height, Bitmap.Config.ARGB_8888)
     bitmap = ssc
     val canvas =  Canvas(bitmap)
     captura.draw(canvas)
     val intent = Intent().apply {
         action = Intent.ACTION_SEND // le estamos diciendo que enviaremos algo
          //putExtra(Intent.EXTRA_TEXT, "se comparte este texto") //aqui va el msms que queramos
         putExtra(Intent.EXTRA_STREAM, Uri.) // sirve para enviar una imagen
         //type = "text/plain"  // es lo que vamos a enviar, podria ser cualquier cosa
        // putExtra(Intent.EXTRA_TITLE, "quieres un curso de compose?")
         type = "image/jpeg"
     }
     val sharedIntent = Intent.createChooser(intent, null)
     startActivity(sharedIntent)
 }*/

    private fun share() {
        val captura: View = window.decorView.rootView

        val ssc: Bitmap =
            Bitmap.createBitmap(captura.width, captura.height, Bitmap.Config.ARGB_8888)
        bitmap = ssc
        val canvas = Canvas(bitmap!!)
        captura.draw(canvas)

        if (bitmap != null) {
            var idGame = SimpleDateFormat("yyyy/MM/dd").format(Date())
            idGame = idGame.replace(":", "")
            idGame = idGame.replace("/", "")

            val path = saveImage(bitmap!!, "${idGame}.jpg")
            val btmUri = Uri.parse(path)

            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            shareIntent.putExtra(Intent.EXTRA_STREAM, btmUri)
            shareIntent.putExtra(Intent.EXTRA_TEXT, "ControlCash,created by Julio Santos")
            shareIntent.type = "image/png"

            val finalShareIntent =
                Intent.createChooser(shareIntent, "Select the app you want to share the game to")
            finalShareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            this.startActivity(finalShareIntent)
        }
    }

    private fun saveImage(bitmap: Bitmap, fileName: String): String? {
        if (bitmap == null) return null
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                put(
                    MediaStore.MediaColumns.RELATIVE_PATH,
                    Environment.DIRECTORY_PICTURES + "/Screenshots"
                )
            }
            val uri = this.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            if (uri != null) {
                this.contentResolver.openOutputStream(uri).use {
                    if (it == null) return@use

                    bitmap.compress(Bitmap.CompressFormat.PNG, 85, it)
                    it.flush()
                    it.close()

                    //añade imagen a galeria
                    MediaScannerConnection.scanFile(this, arrayOf(uri.toString()), null, null)
                }
            }
            return uri.toString()
        }
        val filePath =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/Screenshots").absolutePath

        val dir = File(filePath)
        if (!dir.exists()) dir.mkdirs()
        val file = File(dir, fileName)
        val fileOut = FileOutputStream(file)

        bitmap.compress(Bitmap.CompressFormat.PNG, 85, fileOut)
        fileOut.flush()
        fileOut.close()

        //añade imagen a galeria
        MediaScannerConnection.scanFile(this, arrayOf(file.toString()), null, null)
        return filePath
    }

    private fun menuNavigation() {
        binding.botommNavigation.background = null
    }
}