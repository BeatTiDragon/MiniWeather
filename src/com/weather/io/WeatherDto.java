package com.weather.io;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.weather.ui.CityPanel;

public class WeatherDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Point location = new Point(0, 0);
	
	private List<CityPanel> cityList = new ArrayList<CityPanel>();
	
	private List<CityElement> cityInfo = new ArrayList<CityElement>();
	
	//Ĭ�����һ��Ƥ��
	private int skinFlag = 12;
	//��¼�ڼ�������
	private int cityFlag = 0;
	
	public WeatherDto() {
		if (cityInfo.size() == 0) {
			cityInfo.add(new CityElement("����"));
			cityList.add(new CityPanel("����"));
		}
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public int getSkinFlag() {
		return skinFlag;
	}

	public void setSkinFlag(int skinFlag) {
		this.skinFlag = skinFlag;
	}

	public List<CityPanel> getCityList() {
		return cityList;
	}

	public void setCityList(List<CityPanel> cityList) {
		this.cityList = cityList;
	}

	public List<CityElement> getCityInfo() {
		return cityInfo;
	}

	public void setCityInfo(List<CityElement> cityInfo) {
		this.cityInfo = cityInfo;
	}

	public int getCityFlag() {
		return cityFlag;
	}

	public void setCityFlag(int cityFlag) {
		this.cityFlag = cityFlag;
	}

}
