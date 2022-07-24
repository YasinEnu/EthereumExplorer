package com.yasin.samsung.presentation.ethereum_account

import android.Manifest
import android.app.ProgressDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.budiyev.android.codescanner.*
import com.yasin.samsung.R
import com.yasin.samsung.common.UniversalRecyclerViewAdapter
import com.yasin.samsung.data.remote.dto.ResultItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.statement_item_design.view.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var progressDialog: ProgressDialog
    private lateinit var codeScanner: CodeScanner
    private var mPermissionGranted = false

    private val defaultAddress:String="0xddbd2b932c763ba5b1b7ae3b362eac3e8d40121a"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait...")
        progressDialog.setCancelable(false)

        transactionRV.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
        transactionRV.itemAnimator = DefaultItemAnimator()

        viewModel.getBalanceSuccess.observe(this, Observer {
            transactionRV.visibility=View.GONE
            balanceTV.visibility=View.VISIBLE
            codeScanner.releaseResources()
            scanner_view.visibility=View.GONE
            balanceTV.text="Balance = "+it.result
        })

        viewModel.getStatementSuccess.observe(this, Observer {

            transactionRV.visibility=View.VISIBLE
            balanceTV.visibility=View.GONE
            codeScanner.releaseResources()
            scanner_view.visibility=View.GONE

            transactionRV.adapter=UniversalRecyclerViewAdapter(this@MainActivity,it,R.layout.statement_item_design)
            {universalViewHolder, itemData, position ->
                universalViewHolder.itemView.timeTV.text="Date: "+(itemData as ResultItem).getDate()?:""
                universalViewHolder.itemView.nonceTV.text="Nonce: "+itemData.blockNumber?:""
                universalViewHolder.itemView.fromTV.text="From : "+itemData.from?:""
                universalViewHolder.itemView.toTV.text="To : "+itemData.to?:""
                universalViewHolder.itemView.valueTV.text="Value : "+itemData.value?:""
            }
        })

        viewModel.getBalanceFailed.observe(this, Observer {
            Toast.makeText(this@MainActivity,it,Toast.LENGTH_LONG).show()
        })
        viewModel.progressBarLiveData.observe(this, Observer {
            if (it) {
                progressDialog.show()
            } else {
                progressDialog.dismiss()
            }
        })
        addressET.setText(defaultAddress)

        codeScanner = CodeScanner(this, scanner_view)
        // Parameters (default values)
        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not

        // Callbacks
        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                try {
                    if (it.text.length == 42 && it.text.startsWith("0x")) {
                        addressET.setText(it.text)
                    } else
                        Toast.makeText(this@MainActivity, "QRCode is not valid.", Toast.LENGTH_SHORT).show()
                    codeScanner.releaseResources()
                    scanner_view.visibility=View.GONE
                }catch (exception: Exception){
                    exception.printStackTrace()
                    Log.d("QR-CODE_FAILED", it.text)
                    Toast.makeText(this@MainActivity,"QRCode is not valid.",Toast.LENGTH_SHORT).show()
                    codeScanner.startPreview()
                }
            }
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            runOnUiThread {
                it.printStackTrace()
                Log.d("YASIN", "Camera initialization error: ${it.message}")
                codeScanner.startPreview()
            }
        }

    }

    fun openQrCodeScan(view: View) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                mPermissionGranted = false
                scanner_view.visibility=View.GONE
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 1000);
            } else {
                mPermissionGranted = true
                scanner_view.visibility=View.VISIBLE
                transactionRV.visibility=View.GONE
                balanceTV.visibility=View.GONE
                codeScanner.startPreview()
            }
        } else {
            scanner_view.visibility=View.GONE
            Toast.makeText(this@MainActivity,"Qr Scan Not supported, Need upgraded Android version",Toast.LENGTH_LONG).show()
        }

    }

    fun getBalance(view: View) {

        if (addressET.text.isNullOrBlank()){
            Toast.makeText(this@MainActivity,"Please Enter Account Address",Toast.LENGTH_SHORT).show()
            return;
        }
        viewModel.getBalance(addressET.text.toString())

    }
    fun getTransaction(view: View) {
        if (addressET.text.isNullOrBlank()){
            Toast.makeText(this@MainActivity,"Please Enter Account Address",Toast.LENGTH_LONG).show()
            return;
        }
        viewModel.getStatement(addressET.text.toString())
    }
    override fun onPause() {
        codeScanner.releaseResources()
        scanner_view.visibility=View.GONE
        super.onPause()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        if (requestCode == 1000) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mPermissionGranted = true
                scanner_view.visibility=View.VISIBLE
                transactionRV.visibility=View.GONE
                balanceTV.visibility=View.GONE
                codeScanner.startPreview()
            } else {
                mPermissionGranted = false
                scanner_view.visibility=View.GONE
            }
        }
    }
}