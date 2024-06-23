package com.rafiul.lovenotes.di

import android.content.Context
import com.rafiul.lovenotes.utils.TextToSpeechHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TextToSpeechModule {

    @Singleton
    @Provides
    fun provideTextToSpeechHelper(@ApplicationContext context: Context) = TextToSpeechHelper(context)
}
