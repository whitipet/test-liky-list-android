package com.whitipet.test.likylist.screen.medicine

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.NestedScrollView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.color.MaterialColors
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.whitipet.test.likylist.R
import com.whitipet.test.likylist.screen.base.BaseActivityViewModelFactory
import com.whitipet.test.likylist.utils.*
import com.whitipet.test.likylist.widget.MedicineElement
import kotlin.math.abs

class MedicineActivity : BaseActivityViewModelFactory<MedicineViewModel, MedicineViewModelFactory>() {

	companion object {
		fun intent(context: Context, medicineId: Int): Intent =
			Intent(context, MedicineActivity::class.java).putExtra("medicineId", medicineId)
	}

	override fun provideViewModelFactory(): MedicineViewModelFactory? {
		val medicineId: Int? = intent?.getIntExtra("medicineId", 0)
		return if (medicineId == null || medicineId == 0) {
			Toast.makeText(this, "Item missing", Toast.LENGTH_SHORT).show()
			finish()
			null
		} else {
			MedicineViewModelFactory(medicineId)
		}
	}

	override fun provideViewModel(): Class<MedicineViewModel> = MedicineViewModel::class.java

	override fun beforeOnCreateSuper() {
		super.beforeOnCreateSuper()
		window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
		contentRootView.transitionName = "sharedElement"
		setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
		val transform = MaterialContainerTransform()
		transform.addTarget(contentRootView)
		transform.duration = 300
		val colorBg: Int = MaterialColors.getColor(contentRootView, R.attr.colorBackground)
		transform.setAllContainerColors(colorBg)
		window.sharedElementEnterTransition = transform
		window.sharedElementReturnTransition = transform
	}

	override fun provideContentView() = R.layout.activity_medicine

	private val abl: AppBarLayout by lazy { findViewById(R.id.abl_medicine) }
	private val ctl: View by lazy { findViewById(R.id.ctl_medicine) }
	private val ivImage: View by lazy { findViewById(R.id.iv_medicine_image) }
	private val toolbar: View by lazy { findViewById(R.id.toolbar_medicine) }
	private val btnClose: View by lazy { findViewById(R.id.btn_medicine_close) }
	private val tvToolbarTitle: TextView by lazy { findViewById(R.id.tv_medicine_toolbar_title) }
	private val nsv: NestedScrollView by lazy { findViewById(R.id.nsv_medicine) }
	private val tvTradeLabel: TextView by lazy { findViewById(R.id.tv_item_medicine_trade_label) }
	private val meManufacturerName: MedicineElement by lazy { findViewById(R.id.me_medicine_manufacturer_name) }
	private val mePackagingDescription: MedicineElement by lazy { findViewById(R.id.me_medicine_packaging_description) }
	private val meCompositionDescription: MedicineElement by lazy { findViewById(R.id.me_medicine_composition_description) }
	private val meCompositionInn: MedicineElement by lazy { findViewById(R.id.me_medicine_composition_inn) }
	private val meCompositionPharmForm: MedicineElement by lazy { findViewById(R.id.me_medicine_composition_pharm_form) }

	override fun configure(savedInstanceState: Bundle?) {
		//region Window insets
		val insetsToolbar: Insets = toolbar.obtainInsets()
		val insetsNSV: Insets = nsv.obtainInsets()
		ViewCompat.setOnApplyWindowInsetsListener(contentRootView) { _: View?, windowInsets: WindowInsetsCompat? ->
			val insets: Insets = windowInsets?.systemWindowInsets ?: Insets.NONE
			toolbar.setPadding(insetsToolbar.add(insets.removeBottom()))
			nsv.setPadding(insetsNSV.add(insets.removeTop()))
			toolbar.requestSize { _, height -> ctl.minimumHeight = height }
			windowInsets
		}
		//endregion Window insets

		//region Toolbar
		abl.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
			val fraction = 1 - (abs(verticalOffset.toFloat() / appBarLayout.totalScrollRange).coerceIn(0.0f, 1.0f))
			val fractionAlpha: Float = fraction.subFraction(0.2f, 0.8f)
			ivImage.alpha = fractionAlpha
		})
		//endregion Toolbar

		btnClose.setOnClickListener { finish() }

		nsv.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
			tvToolbarTitle.isActivated = scrollY >= tvTradeLabel.bottom
		})

		viewModel.medicineObservable.observe(this, {
			if (it != null) {
				ivImage.setBackgroundColor(it.color)
				tvToolbarTitle.text = it.tradeLabel
				if (!it.tradeLabel.isNullOrBlank()) {
					tvTradeLabel.visibility = View.VISIBLE
					tvTradeLabel.text = it.tradeLabel
				} else {
					tvTradeLabel.visibility = View.GONE
				}
				if (!it.manufacturerName.isNullOrBlank()) {
					meManufacturerName.visibility = View.VISIBLE
					meManufacturerName.setText(it.manufacturerName)
				} else {
					meManufacturerName.visibility = View.GONE
				}
				if (!it.packagingDescription.isNullOrBlank()) {
					mePackagingDescription.visibility = View.VISIBLE
					mePackagingDescription.setText(it.packagingDescription)
				} else {
					mePackagingDescription.visibility = View.GONE
				}
				if (!it.description.isNullOrBlank()) {
					meCompositionDescription.visibility = View.VISIBLE
					meCompositionDescription.setText(it.description)
				} else {
					meCompositionDescription.visibility = View.GONE
				}
				if (!it.inn.isNullOrBlank()) {
					meCompositionInn.visibility = View.VISIBLE
					meCompositionInn.setText(it.inn)
				} else {
					meCompositionInn.visibility = View.GONE
				}
				if (!it.pharmForm.isNullOrBlank()) {
					meCompositionPharmForm.visibility = View.VISIBLE
					meCompositionPharmForm.setText(it.pharmForm)
				} else {
					meCompositionPharmForm.visibility = View.GONE
				}
			} else {
				ivImage.setBackgroundColor(0x1A8E8E8E)
				tvToolbarTitle.text = null
				tvTradeLabel.text = null
				tvTradeLabel.visibility = View.GONE
				meManufacturerName.visibility = View.GONE
				mePackagingDescription.visibility = View.GONE
				meCompositionDescription.visibility = View.GONE
				meCompositionInn.visibility = View.GONE
				meManufacturerName.visibility = View.GONE
				meCompositionPharmForm.visibility = View.GONE
			}
		})
	}
}