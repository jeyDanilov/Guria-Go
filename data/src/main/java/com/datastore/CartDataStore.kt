package com.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

 val Context.cartDataStore by preferencesDataStore(name = "cart")