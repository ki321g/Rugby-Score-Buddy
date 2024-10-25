package org.setu.rugbyscorebuddy.activities

import android.R.attr.text
import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import org.setu.rugbyscorebuddy.databinding.ActivityRugbyScoreMapsBinding
import org.setu.rugbyscorebuddy.databinding.ContentRugbyScoreMapsBinding
import org.setu.rugbyscorebuddy.main.MainApp

class RugbyScoreMapsActivity : AppCompatActivity(), GoogleMap.OnMarkerClickListener {

    private lateinit var binding: ActivityRugbyScoreMapsBinding
    private lateinit var contentBinding: ContentRugbyScoreMapsBinding
    lateinit var map: GoogleMap
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRugbyScoreMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        app = application as MainApp

        contentBinding = ContentRugbyScoreMapsBinding.bind(binding.root)
        contentBinding.mapView.onCreate(savedInstanceState)

        contentBinding.mapView.getMapAsync {
            map = it
            configureMap()
        }
    }

    private fun configureMap() {
        map.uiSettings.isZoomControlsEnabled = true
        app.rugbygames.findAll().forEach {
            val loc = LatLng(it.lat, it.lng)
            val homeScore = (it.homeTeamTries * 5) + (it.homeTeamConversions * 2 ) + (it.homeTeamPenalties * 3)
            val awayScore = (it.awayTeamTries * 5) + (it.awayTeamConversions * 2 ) + (it.awayTeamPenalties * 3)
            val homeTries = it.homeTeamTries
            val awayTries = it.awayTeamTries
            val homeConversions = it.homeTeamConversions
            val awayConversions = it.awayTeamConversions
            val homePenalties = it.homeTeamPenalties
            val awayPenalties = it.awayTeamPenalties
            val options = MarkerOptions()
                .title(it.homeTeam +  " vs " + it.awayTeam)
                .snippet("GPS : $loc")
                .position(loc)

            map.addMarker(options)?.tag = it.id
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
            map.setOnMarkerClickListener(this)
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        //contentBinding.homeTeam.text = marker.title
        val tag = marker.tag as Long
        val rugbyGame = app.rugbygames.findById(tag)

        val homeTries = rugbyGame!!.homeTeamTries
        val awayTries = rugbyGame!!.awayTeamTries
        val homeConversions = rugbyGame!!.homeTeamConversions
        val awayConversions = rugbyGame!!.awayTeamConversions
        val homePenalties = rugbyGame!!.homeTeamPenalties
        val awayPenalties = rugbyGame!!.awayTeamPenalties
        val homeScore = (homeTries * 5) + (homeConversions * 2 ) + (homePenalties * 3)
        val awayScore = (awayTries * 5) + (awayConversions * 2 ) + (awayPenalties* 3)

        contentBinding.gameTeams.text = rugbyGame!!.homeTeam + " vs " + rugbyGame!!.awayTeam
        contentBinding.gameResults.text = Html.fromHtml("<b>Score :</b> $homeScore : $awayScore<br/>" +
                "<b>Tries :</b> $homeTries : $awayTries<br/>" +
                "<b>Conversions :</b> $homeConversions : $awayConversions<br/>" +
                "<b>Penalties :</b> $homePenalties : $awayPenalties", Html.FROM_HTML_MODE_LEGACY)

        Picasso.get().load(rugbyGame!!.image).into(contentBinding.gameImage)

        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        contentBinding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        contentBinding.mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        contentBinding.mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        contentBinding.mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        contentBinding.mapView.onSaveInstanceState(outState)
    }
}