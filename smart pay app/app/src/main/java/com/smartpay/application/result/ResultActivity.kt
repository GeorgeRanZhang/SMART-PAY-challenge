package com.smartpay.application.result

import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smartpay.application.BaseActivity
import com.smartpay.application.R
import com.smartpay.application.common.Constants
import com.smartpay.application.util.TLVUtils.initTlvListByHexString

class ResultActivity : BaseActivity() {

    private lateinit var mAdapter: ResultAdapter
    private lateinit var mRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        setToolbar(getString(R.string.result), true)
        mRecyclerView = findViewById<RecyclerView>(R.id.rv_Result)
        mAdapter = ResultAdapter(this)
        intent.getStringExtra(Constants.RESPONSE_DATA)?.let { initTlvListByHexString(it) }
        mAdapter.submitList(intent.getStringExtra(Constants.RESPONSE_DATA)?.let { initTlvListByHexString(it) })
        mRecyclerView.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@ResultActivity)
            itemAnimator = null
            addItemDecoration(
                DividerItemDecoration(
                    this@ResultActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        setResult(1)
    }
}