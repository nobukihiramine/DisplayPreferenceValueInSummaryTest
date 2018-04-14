package com.hiramine.displaypreferencevalueinsummarytest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity
{
	private static final int    REQUEST_MYPREFERENCEACTIVTY   = 1; // 設定

	TextView textviewOnOffPreference1;
	TextView textviewOnOffPreference2;
	TextView textviewTextPreference;
	TextView textviewListPreference;
	TextView textviewMultiSelectListPreference;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );

		// テキストビュー
		textviewOnOffPreference1 = (TextView)findViewById( R.id.textview_onoffpreference1 );
		textviewOnOffPreference2 = (TextView)findViewById( R.id.textview_onoffpreference2 );
		textviewTextPreference = (TextView)findViewById( R.id.textview_textpreference );
		textviewListPreference = (TextView)findViewById( R.id.textview_listpreference );
		textviewMultiSelectListPreference = (TextView)findViewById( R.id.textview_multiselectlistpreference );

		// 前回設定の読み込み
		updateSettings( false );
	}

	// オプションメニュー作成時の処理
	@Override
	public boolean onCreateOptionsMenu( Menu menu )
	{
		getMenuInflater().inflate( R.menu.activity_main, menu );
		return true;
	}

	// オプションメニューのアイテム選択時の処理
	@Override
	public boolean onOptionsItemSelected( MenuItem item )
	{
		switch( item.getItemId() )
		{
			case R.id.menuitem_settings:
				Intent intent = new Intent( this, MyPreferenceActivity.class );
				startActivityForResult( intent, REQUEST_MYPREFERENCEACTIVTY );
				return true;
		}
		return false;
	}

	// 別アクティビティの操作結果
	@Override
	protected void onActivityResult( int requestCode, int resultCode, Intent data )
	{
		switch( requestCode )
		{
			case REQUEST_MYPREFERENCEACTIVTY: // Preferenceアクティビティ
				// 設定変更の反映
				updateSettings( false );
				break;
		}
		super.onActivityResult( requestCode, resultCode, data );
	}

	// 設定値の読み書き
	private void updateSettings( boolean bSave )
	{
		// 未初期化パラメータに、初期値設定
		PreferenceManager.setDefaultValues( this, R.xml.preferences, false );

		SharedPreferences        sharedpreferences = PreferenceManager.getDefaultSharedPreferences( this );
		SharedPreferences.Editor editor            = sharedpreferences.edit();

		if( bSave )
		{
		}
		else
		{
			boolean bValue;
			String strValue;

			bValue = sharedpreferences.getBoolean( "on_off_preference_1", false );
			textviewOnOffPreference1.setText( bValue ? "true" : "false" );

			bValue = sharedpreferences.getBoolean( "on_off_preference_2", false );
			textviewOnOffPreference2.setText( bValue ? "true" : "false" );

			strValue = sharedpreferences.getString( "text_preference", "" );
			textviewTextPreference.setText( strValue );

			strValue = sharedpreferences.getString( "list_preference", "" );
			textviewListPreference.setText( strValue );

			Set<String> setstrEmpty = new HashSet<String>();
			Set<String> setstrValue = sharedpreferences.getStringSet( "multi_select_list_preference", setstrEmpty );
			textviewMultiSelectListPreference.setText( setstrValue.toString() );
		}

		// 設定値の書き込みの実施
		if( bSave )
		{
			editor.apply();
		}
	}
}
