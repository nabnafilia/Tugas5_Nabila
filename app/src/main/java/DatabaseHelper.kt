    import android.content.ContentValues
    import android.content.Context
    import android.database.sqlite.SQLiteDatabase
    import android.database.sqlite.SQLiteOpenHelper

    class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

        companion object {
            const val DATABASE_NAME = "mydatabase.db"
            const val DATABASE_VERSION = 1
            const val TABLE_NAME = "items"
            const val COLUMN_ID = "id"
            const val COLUMN_NAME = "name"
        }

        // Membuat tabel SQLite saat database dibuat
        override fun onCreate(db: SQLiteDatabase) {
            val createTable = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_NAME TEXT)"
            db.execSQL(createTable)
        }

        // Jika database di-upgrade (versi baru), maka tabel lama akan di-drop dan dibuat ulang
        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
            onCreate(db)
        }

        // Fungsi untuk menambah data baru ke dalam tabel
        fun insertItem(name: String): Long {
            val db = this.writableDatabase
            val values = ContentValues().apply {
                put(COLUMN_NAME, name)
            }
            return db.insert(TABLE_NAME, null, values)
        }

        // Fungsi untuk mendapatkan semua data dari tabel dalam bentuk list
        fun getAllItems(): MutableList<String> {
            val itemList = mutableListOf<String>()
            val db = this.readableDatabase
            val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
            if (cursor.moveToFirst()) {
                do {
                    val itemName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                    itemList.add(itemName)
                } while (cursor.moveToNext())
            }
            cursor.close()
            return itemList
        }
    }
