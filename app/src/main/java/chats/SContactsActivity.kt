package chats

import data.SContactModel
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.strng.R
import com.example.strng.databinding.SActivityContactsBinding
import data.SUserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import utils.SApplication

class SContactsActivity : AppCompatActivity() {
    private lateinit var contactsBinding: SActivityContactsBinding
    private val db = SApplication.getDB().userDao()
    private val contactsViewModel by lazy {
        ViewModelProvider(this).get(SContactsViewModel::class.java)
    }

    companion object{
        const val PERMISSIONS_REQUEST_READ_CONTACTS= 100
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contactsBinding = SActivityContactsBinding.inflate(layoutInflater)
        setContentView(contactsBinding.root)
        initObserver()
        initData()
        initClickListeners()

        contactsBinding.rvContacts.layoutManager = LinearLayoutManager(this)
        contactsBinding.rvContacts.addItemDecoration(DividerItemDecorator(resources.getDrawable(R.drawable.divider_style, null)))
    }

    private fun initData(){
        GlobalScope.launch(IO) {
            val list = db?.getUserInfo()?.contacts?.toMutableList()
            if (list.isNullOrEmpty()) {
                println("infoDB from Phone")
                loadContacts()
            } else {
                println("infoDB from Database")
                launch(Dispatchers.Main) {
                    contactsViewModel.contactList.value = list
                }
            }
        }
    }
    private fun initObserver() {
        with(contactsViewModel) {
            contactList.observe(this@SContactsActivity, {
                println(it.toString())
                println(it.size)
                contactsBinding.rvContacts.adapter = SContactsAdapter(it)
                GlobalScope.launch(IO){
                    db?.insertUserInfo(SUserEntity(null, "guru", it.toList(), null))
                }
            })
        }
    }

    private fun initClickListeners(){
        with(contactsBinding){
            ibBack.setOnClickListener {
                finish()
            }
            ibMenu.setOnClickListener {
                PopupMenu(this@SContactsActivity, it).apply {
                    menuInflater.inflate(R.menu.menu_contacts_screen, menu)
                    setOnMenuItemClickListener {  it1->
//                        when(it1){
//                            R.id.refresh_contacts -> initData()
//                        }
                        println("Menu clicked   ${it1.title}")
                        true
                    }
                }.show()
            }
        }
    }
    private fun loadContacts() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.READ_CONTACTS),
                PERMISSIONS_REQUEST_READ_CONTACTS
            )
            //callback onRequestPermissionsResult
        } else {
            GlobalScope.launch(IO) {
                val list = mutableListOf<SContactModel>()
                val uri = ContactsContract.Contacts.CONTENT_URI
                val cursor = contentResolver.query(uri, null, null, null, null)
                var name: String? = ""
                var id = ""
                cursor?.apply {
                    if (count > 0) {
                        while (moveToNext()) {
                            id = getString(getColumnIndex(ContactsContract.Contacts._ID))
                            name = getString(getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                            val phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                            val selection =
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " =?"
                            val phoneCursor = contentResolver.query(
                                phoneUri, null, selection,
                                arrayOf(id), null
                            )
                            phoneCursor?.let {
                                if (it.moveToNext()) {
                                    val number =
                                        it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                                    println("$name   -->   $number")
                                    if (name != null) {
                                        list.add(SContactModel(name, number))
                                    }

                                }
                                it.close()
                            }
                        }
                    }
                    close()
                }

                launch(Dispatchers.Main) {
                    println(list.size)
                    contactsViewModel.contactList.value = list
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadContacts()
            } else {
                //  toast("Permission must be granted in order to display contacts information")
            }
        }
    }
}