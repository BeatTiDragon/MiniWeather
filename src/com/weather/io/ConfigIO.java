package com.weather.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.Key;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.swing.JOptionPane;

import com.alibaba.fastjson.JSONArray;
import com.weather.engine.Weather;

@SuppressWarnings("serial")
public class ConfigIO implements Serializable {
	  private WeatherDto dto;
	  private Key key;
	  private ObjectOutputStream out;
	  private ObjectInputStream in;
	  private final static String CONFIG_PATH = "data/config.db";
	  private final static String KEY_PATH = "data/key.db";

	  public ConfigIO(WeatherDto dto) {
		  this.dto = dto;
		  this.readConfig();
	  }

	  private void readConfig() {
		  try {	
			  //����ļ������ڣ������ļ�
			  if (!new File(CONFIG_PATH).exists() || !new File(KEY_PATH).exists()) {
				  //�����������������磬��ʧ���򲻴����ļ�
				  JSONArray json = Weather.getWeatherJson(dto.getCityInfo().get(0).getCityName());
				  new File("data").mkdirs();
				  new File(CONFIG_PATH).createNewFile();
				  new File(KEY_PATH).createNewFile();
				  
				  //ָ���㷨,����ΪDES
			      KeyGenerator kg = KeyGenerator.getInstance("DES", "SunJCE");
	              //ָ����Կ����,����Խ��,����ǿ��Խ��
	              kg.init(56);
	              //������Կ
	              key = kg.generateKey();
	              //�����Կ
	              out = new ObjectOutputStream(new 
	            		  BufferedOutputStream(new FileOutputStream(KEY_PATH)));
	              out.writeObject(key);
	              out.close();
	              				  
				//�״����г�ʼ��
				  Date date = new Date();
			      dto.getCityInfo().get(0).setWeaUpdateTime(date);
			      dto.getCityInfo().get(0).setWeatherJson(json);
				  dto.getCityInfo().get(0).setAqiJson(Weather.getAQIJson(dto.getCityInfo().get(0).getCityName()));
				  dto.getCityInfo().get(0).setAqiUpdateTime(date);
			  }
			  else {
				  //��ȡ��Կ
                  in = new ObjectInputStream(new
                        BufferedInputStream(new FileInputStream(KEY_PATH)));
                  key = (Key)in.readObject();
                  //��ȡ����
				  Cipher cipher = Cipher.getInstance("DES");
	              cipher.init(Cipher.DECRYPT_MODE, key);
				  in = new ObjectInputStream(new CipherInputStream(new
		                    BufferedInputStream(new FileInputStream(CONFIG_PATH)),cipher));
				  dto = (WeatherDto)in.readObject();
				  in.close();
			  }
		  } catch (Exception e) {
			// TODO Auto-generated catch block
			  if (e.getMessage().equals("��ǰ������")) {
				  JOptionPane.showMessageDialog(null, "��ǰ�����ߣ�������", "����", JOptionPane.INFORMATION_MESSAGE);
				  System.exit(0);
		      }
			  e.printStackTrace();
		  }
	  }

	  public void saveConfig(){
		  try {
			  Cipher cipher = Cipher.getInstance("DES");
              cipher.init(Cipher.ENCRYPT_MODE, key);
			  out = new ObjectOutputStream(new FileOutputStream(CONFIG_PATH));
              out = new ObjectOutputStream(new CipherOutputStream(new
                      BufferedOutputStream(new FileOutputStream(CONFIG_PATH)), cipher));
			  out.writeObject(dto);
			  out.close();
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
	  }	
	  /**
	  * @return ��������
	  */
	  public WeatherDto getConfig(){
		  return this.dto;
	  }
}
