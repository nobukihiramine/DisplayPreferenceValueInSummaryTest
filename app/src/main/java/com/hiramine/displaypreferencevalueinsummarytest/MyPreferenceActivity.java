package com.hiramine.displaypreferencevalueinsummarytest;

import android.content.SharedPreferences;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Set;

public class MyPreferenceActivity extends AppCompatActivity
{
	public static class MyPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener
	{
		@Override
		public void onCreate( final Bundle savedInstanceState )
		{
			super.onCreate( savedInstanceState );

			// 設定画面を定義したXMLを読み込む
			addPreferencesFromResource( R.xml.preferences );

			// Summaryの初期化
			initSummary( getPreferenceScreen() );
		}

		@Override
		public void onResume()
		{
			super.onResume();

			// OnSharedPreferenceChangeListenerの登録
			getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener( this );
		}

		@Override
		public void onPause()
		{
			// OnSharedPreferenceChangeListenerの解除
			getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener( this );

			super.onPause();
		}

		public void onSharedPreferenceChanged( SharedPreferences sharedPreferences,
											   String key )
		{
			updateSummary( findPreference( key ) );
		}

		// Summaryの初期化
		private void initSummary( Preference preference )
		{
			if( preference instanceof PreferenceGroup )
			{
				PreferenceGroup pGrp = (PreferenceGroup)preference;
				for( int i = 0; i < pGrp.getPreferenceCount(); i++ )
				{
					initSummary( pGrp.getPreference( i ) );
				}
			}
			else
			{
				updateSummary( preference );
			}
		}

		// Summaryの更新
		private void updateSummary( Preference preference )
		{
			// ListPreference
			if( preference instanceof ListPreference )
			{
				ListPreference listPreference = (ListPreference)preference;
				preference.setSummary( listPreference.getEntry() );
			}
			// EditTextPreference
			if( preference instanceof EditTextPreference )
			{
				EditTextPreference editTextPreference = (EditTextPreference)preference;
				if( preference.getTitle().toString().toLowerCase().contains( "password" ) )
				{
					preference.setSummary( "******" );
				}
				else
				{
					preference.setSummary( editTextPreference.getText() );
				}
			}
			// MultiSelectListPreference
			if( preference instanceof MultiSelectListPreference )
			{
				MultiSelectListPreference multiSelectListPreference = (MultiSelectListPreference)preference;
				Set<String> setstrValue = multiSelectListPreference.getValues();
				String strvalue = setstrValue.toString();
				CharSequence[] aEntry = multiSelectListPreference.getEntries();
				String strSummary = "";
				for( String strValue : setstrValue )
				{
					int index = multiSelectListPreference.findIndexOfValue( strValue );
					if( !strSummary.isEmpty() )
					{
						strSummary += ", ";
					}
					strSummary += aEntry[index].toString();
				}
				preference.setSummary( strSummary );
			}
		}
	}

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		//setContentView( R.layout.activity_my_preference );
		getFragmentManager().beginTransaction().replace( android.R.id.content, new MyPreferenceFragment() ).commit();
	}
}
