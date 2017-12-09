package com.fibelatti.raffler.data.quickdecision

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.os.Parcel
import android.os.Parcelable
import com.fibelatti.raffler.core.extensions.createParcel
import com.fibelatti.raffler.data.quickdecision.QuickDecision.Companion.COLUMN_ID
import com.fibelatti.raffler.data.quickdecision.QuickDecision.Companion.COLUMN_LOCALE
import com.fibelatti.raffler.data.quickdecision.QuickDecision.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME,
        primaryKeys = [COLUMN_ID, COLUMN_LOCALE])
data class QuickDecision(
        @ColumnInfo(name = COLUMN_ID)
        val id: String,
        @ColumnInfo(name = COLUMN_LOCALE)
        val locale: String,
        @ColumnInfo(name = COLUMN_NAME)
        val name: String,
        @ColumnInfo(name = COLUMN_VALUES)
        val values: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(locale)
        parcel.writeString(name)
        parcel.writeString(values)
    }

    override fun describeContents() = 0

    companion object {
        val CREATOR = createParcel { QuickDecision(it) }

        const val TABLE_NAME = "quick_decision"
        const val COLUMN_ID = "quick_decision_key"
        const val COLUMN_LOCALE = "quick_decision_locale"
        const val COLUMN_NAME = "quick_decision_name"
        const val COLUMN_VALUES = "quick_decision_values"

        const val CREATE_TABLE = ("CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME
                + " ("
                + COLUMN_ID
                + " TEXT NOT NULL,"
                + COLUMN_LOCALE
                + " TEXT NOT NULL,"
                + COLUMN_NAME
                + " TEXT NOT NULL,"
                + COLUMN_VALUES
                + " TEXT NOT NULL,"
                + " PRIMARY KEY (" + COLUMN_ID + "," + COLUMN_LOCALE + ")"
                + ");")


        const val DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME

        const val INITIAL_SETUP = ("INSERT OR REPLACE INTO "
                + TABLE_NAME
                + " VALUES "
                + "('yes_or_no', 'en', 'Yes or No', 'Yes,No'),"
                + "('yes_or_no', 'pt', 'Sim ou Não', 'Sim,Não'),"
                + "('yes_or_no', 'es', 'Sí o No', 'Sí,No'),"
                + "('throw_a_coin', 'en', 'Throw a coin', 'Heads,Tails'),"
                + "('throw_a_coin', 'pt', 'Cara ou Coroa?', 'Cara,Coroa'),"
                + "('throw_a_coin', 'es', '¿Cara o Cruz?', 'Cara,Cruz'),"
                + "('dice_d6', 'en', 'Roll a dice', 'It landed 1,It landed 2,It landed 3,It landed 4,It landed 5,It landed 6'),"
                + "('dice_d6', 'pt', 'Jogar um dado', 'Saiu 1,Saiu 2,Saiu 3,Saiu 4,Saiu 5,Saiu 6'),"
                + "('dice_d6', 'es', 'Tirar un dado', 'Salió 1,Salió 2,Salió 3,Salió 4,Salió 5,Salió 6'),"
                + "('dice_d20', 'en', 'Roll a d20', 'It landed 1,It landed 2,It landed 3,It landed 4,It landed 5,"
                + "It landed 6,It landed 7,It landed 8,It landed 9,It landed 10,It landed 11,It landed 12,It landed 13,"
                + "It landed 14,It landed 15,It landed 16,It landed 17,It landed 18,It landed 19,It landed 20... DOUBLE DAMAGE!'),"
                + "('dice_d20', 'pt', 'Jogar um d20', 'Saiu 1,Saiu 2,Saiu 3,Saiu 4,Saiu 5,Saiu 6,Saiu 7,Saiu 8,"
                + "Saiu 9,Saiu 10,Saiu 11,Saiu 12,Saiu 13,Saiu 14,Saiu 15,Saiu 16,Saiu 17,Saiu 18,Saiu 19,Saiu 20... DOUBLE DAMAGE!'),"
                + "('dice_d20', 'es', 'Tirar un d20', 'Sailó 1,Sailó 2,Sailó 3,Sailó 4,Sailó 5,Sailó 6,Sailó 7,"
                + "Sailó 8,Sailó 9,Sailó 10,Sailó 11,Sailó 12,Sailó 13,Sailó 14,Sailó 15,Sailó 16,Sailó 17,Sailó 18,Sailó 19,Sailó 20... DOUBLE DAMAGE!')")
    }
}
