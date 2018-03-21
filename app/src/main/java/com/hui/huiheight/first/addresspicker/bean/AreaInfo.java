package com.hui.huiheight.first.addresspicker.bean;

/**
 * 区域信息
 * 
 * @author xiong
 *
 */
@SuppressWarnings("serial")
public class AreaInfo extends VoBase {
	private Integer areaId;
	private String areaName;
	public Integer getAreaId() {
		return this.areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
		return;
	}
	public String getAreaName() {
		return this.areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
		return;
	}

}
