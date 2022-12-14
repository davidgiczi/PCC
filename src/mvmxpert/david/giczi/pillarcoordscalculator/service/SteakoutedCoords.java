package mvmxpert.david.giczi.pillarcoordscalculator.service;

import java.text.DecimalFormat;

import mvmxpert.david.giczi.pillarcoordscalculator.utils.BaseType;

public class SteakoutedCoords implements Comparable<SteakoutedCoords> {

	private int id;
	private Point centerPoint;
	private Point directionPoint;
	private String pointID;
	private String stkPointID;
	private String comment;
	private String sign;
	private int rotation;
	private BaseType baseType;
	private String pathDistance;
	private String centerToHalfwayOfHolesDistance;
	private String horizontalDistanceFromSideOfHole;
	private String verticalDistanceFromSideOfHole;
	private double XcoordForDesignPoint;
	private double YcoordForDesignPoint;
	private double XcoordForSteakoutPoint;
	private double YcoordForSteakoutPoint;
	private DecimalFormat df;
	
	public SteakoutedCoords(BaseType baseType, String pointID) {
		this.baseType = baseType;
		this.pointID = pointID;
		df = new DecimalFormat("0.000");
	}
	
	public void setCenterPoint(Point centerPoint) {
		this.centerPoint = centerPoint;
	}
	
	public void setDirectionPoint(Point directionPoint) {
		this.directionPoint = directionPoint;
	}

	public void setXcoordForDesignPoint(double xcoordForDesignPoint) {
		XcoordForDesignPoint = xcoordForDesignPoint;
	}

	public void setYcoordForDesignPoint(double ycoordForDesignPoint) {
		YcoordForDesignPoint = ycoordForDesignPoint;
	}

	public void setXcoordForSteakoutPoint(double xcoordForSteakoutPoint) {
		XcoordForSteakoutPoint = xcoordForSteakoutPoint;
	}

	public void setYcoordForSteakoutPoint(double ycoordForSteakoutPoint) {
		YcoordForSteakoutPoint = ycoordForSteakoutPoint;
	}
	
	public void setPathDistance(String pathDistance) {
		this.pathDistance = pathDistance;
	}
	
	public void setCenterToHalfwayOfHolesDistance(String centerToHalfwayOfHolesDistance) {
		this.centerToHalfwayOfHolesDistance = centerToHalfwayOfHolesDistance;
	}

	public void setHorizontalDistanceFromSideOfHole(String horizontalDistanceFromSideOfHole) {
		this.horizontalDistanceFromSideOfHole = horizontalDistanceFromSideOfHole;
	}

	public void setVerticalDistanceFromSideOfHole(String verticalDistanceFromSideOfHole) {
		this.verticalDistanceFromSideOfHole = verticalDistanceFromSideOfHole;
	}
	
	public String getStkPointID() {
		return stkPointID;
	}

	public void setStkPointID(String stkPointID) {
		this.stkPointID = stkPointID;
	}
	
	public String getSteakoutedPointData() {
		return pointID + 
				"\t" + comment +
				"\t" + sign +
				"\t" + String.valueOf(XcoordForDesignPoint).replace('.', ',') + 
				"\t" + String.valueOf(YcoordForDesignPoint).replace('.', ',') + 
				"\t" + String.valueOf(XcoordForSteakoutPoint).replace('.', ',') +
				"\t" + String.valueOf(YcoordForSteakoutPoint).replace('.', ',') +
			    "\t" + getDeltaX().replace('.', ',') + 
				"\t" + getDeltaY().replace('.', ',');
	}
	
	public String getPointID() {
		return pointID;
	}

	public String getDeltaX() {
		return df.format(XcoordForDesignPoint - XcoordForSteakoutPoint);
	}
	
	public String getDeltaY() {
		return df.format(YcoordForDesignPoint - YcoordForSteakoutPoint);
	}
	
	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPointID(String pointID) {
		this.pointID = pointID;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setBaseType(BaseType baseType) {
		this.baseType = baseType;
	}

	public void setMetaData() {
		switch (baseType) {
		case PLATE_BASE:
			if( rotation == 0 ) 
			setMetaDataForPlateBaseControlledPoint();
			else 
			setMetaDataForRotatedPlateBaseControlledPoint();
			break;
		case WEIGHT_BASE:
			if( rotation == 0 ) 
			setMetaDataForWeightBaseControlledPoint();
			else 
			setMetaDataForRotatedWeightBaseControlledPoint();
		}
	}
	
	private int getPointIntegerID(Point point) {
		int id = 0;
		try {
			id = Integer.parseInt(point.getPointID());
		} catch (Exception e) {
			
			if( point.equals(centerPoint) )
				id = 1;
			else if( point.equals(directionPoint) )
				id = 2;
		}
		return id;
	}

	private void setMetaDataForWeightBaseControlledPoint() {
		
		String[] pointIDComponents = pointID.split("_");
		
		if( pointIDComponents.length == 1) {
			setCenterPoint();
			return;
		}
		
		switch (pointIDComponents[1]) {
		case "1":
			id = 1;
			sign = "kar?? szeggel";
			comment = "a nyomvonal ir??nya az oszlop k??zep??t??l " + pathDistance + " m??terre " + 
			(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "el??re" : "h??tra");
			break;
		case "2":
			id = 2;
			sign = "kar?? szeggel";
			comment = "a nyomvonalra mer??leges tengely ir??nya az oszlop k??zep??t??l " + pathDistance + " m??terre " +
			(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "jobbra" : "balra");
			break;
		case "3":
			id = 3;
			sign = "kar?? szeggel";
			comment = "a nyomvonal ir??nya az oszlop k??zep??t??l " + pathDistance + " m??terre " +
			(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "h??tra" : "el??re");
			break;
		case "4":
			id = 4;
			sign = "kar?? szeggel";
			comment = "a nyomvonalra mer??leges tengely ir??nya az oszlop k??zep??t??l " + pathDistance + " m??terre " +
			(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "balra" : "jobbra");
			break;
		case "5":
			id = 5;
			sign = "kar?? szeggel";
			comment = "a nyomvonal ir??nya az oszlop k??zep??t??l " + centerToHalfwayOfHolesDistance + " m??terre " +
			(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "el??re" : "h??tra");
			break;
		case "6": 
			id = 6;
			sign = "kar?? szeggel";
			comment = "a nyomvonalra mer??leges tengely ir??nya az oszlop k??zep??t??l " + centerToHalfwayOfHolesDistance + " m??terre " +
			(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "jobbra" : "balra");
			break;
		case "7":
			id = 7;
			sign = "kar?? szeggel";
			comment = "a nyomvonal ir??nya az oszlop k??zep??t??l " + centerToHalfwayOfHolesDistance + " m??terre " +
			(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "h??tra" : "el??re");
			break;
		case "8":
			id = 8;
			sign = "kar?? szeggel";
			comment = "a nyomvonalra mer??leges tengely ir??nya az oszlop k??zep??t??l " + centerToHalfwayOfHolesDistance + " m??terre " +
			(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "balra" : "jobbra");
			break;
		case "9":
			id = 9;
			sign = "kar??";
			comment ="az oszlop l??b g??d??r sz??le";
			break;
		case "10":
			id = 10;
			sign = "kar??";
			comment ="az oszlop l??b g??d??r sz??le";
			break;
		case "11":
			id = 11;
			sign = "kar??";
			comment ="az oszlop l??b g??d??r sz??le";
			break;
		case "12":
			id = 12;
			sign = "kar??";
			comment ="az oszlop l??b g??d??r sz??le";
			break;
		case "13":
			id = 13;
			sign = "kar??";
			comment ="az oszlop l??b g??d??r sz??le";
			break;
		case "14":
			id = 14;
			sign = "kar??";
			comment ="az oszlop l??b g??d??r sz??le";
			break;
		case "15":
			id = 15;
			sign = "kar??";
			comment ="az oszlop l??b g??d??r sz??le";
			break;
		case "16":
			id = 16;
			sign = "kar??";
			comment ="az oszlop l??b g??d??r sz??le";
			break;
		case "17":
			id = 17;
			sign = "kar??";
			comment ="az oszlop l??b g??d??r sz??le";
			break;
		case "18":
			id = 18;
			sign = "kar??";
			comment ="az oszlop l??b g??d??r sz??le";
			break;
		case "19":
			id = 19;
			sign = "kar??";
			comment ="az oszlop l??b g??d??r sz??le";
			break;
		case "20":
			id = 20;
			sign = "kar??";
			comment ="az oszlop l??b g??d??r sz??le";
			break;
		case "21":
			id = 21;
			sign = "kar??";
			comment ="az oszlop l??b g??d??r sz??le";
			break;
		case "22":
			id = 22;
			sign = "kar??";
			comment ="az oszlop l??b g??d??r sz??le";
			break;
		case "23":
			id = 23;
			sign = "kar??";
			comment ="az oszlop l??b g??d??r sz??le";
			break;
		case "24":
			id = 24;
			sign = "kar??";
			comment ="az oszlop l??b g??d??r sz??le";
		}
		
		
	}
	
	private void setMetaDataForPlateBaseControlledPoint() {
		
		String[] pointIDComponents = pointID.split("_");
		
		if( pointIDComponents.length == 1) {
			setCenterPoint();
			return;
		}
		
		switch (pointIDComponents[1]) {
		case "1":
			id = 1;
			sign = "kar??";
			comment = "az alap g??dr??nek pontja";
			break;
		case "2":
			id = 2;
			sign = "kar??";
			comment = "az alap g??dr??nek pontja";
			break;
		case "3":
			id = 3;
			sign = "kar??";
			comment = "az alap g??dr??nek pontja";
			break;
		case "4":
			id = 4;
			sign = "kar??";
			comment = "az alap g??dr??nek pontja";
			break;
		case "5":
			id = 5;
			sign = "kar?? szeggel";
			comment = "a nyomvonalra mer??leges tengely ir??nya az alap g??dr??nek sz??l??t??l " 
			+ verticalDistanceFromSideOfHole + " m??terre " +
			(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "balra" : "jobbra");
			break;
		case "6":
			id = 6;
			sign = "kar?? szeggel";
			comment = "a nyomvonal ir??nya az alap g??dr??nek sz??l??t??l " 
			+ horizontalDistanceFromSideOfHole + " m??terre " +
			(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "el??re" : "h??tra");
			break;
		case "7":
			id = 7;
			sign = "kar?? szeggel";
			comment = "a nyomvonalra mer??leges tengely ir??nya az alap g??dr??nek sz??l??t??l " 
			+ verticalDistanceFromSideOfHole + " m??terre " +
			(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "jobbra" : "balra");;
			break;
		case "8":
			id = 8;
			sign = "kar?? szeggel";
			comment = "a nyomvonal ir??nya az alap g??dr??nek sz??l??t??l " 
			+ horizontalDistanceFromSideOfHole + " m??terre " +
			(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "h??tra" : "el??re");
		}
		
	}
	
	private void setMetaDataForRotatedWeightBaseControlledPoint() {
		
		String[] pointIDComponents = pointID.split("_");
		
		if( pointIDComponents.length == 1) {
			setCenterPoint();
			return;
		}
		
		switch (pointIDComponents[1]) {
		case "1":
			id = 1;
			sign = "kar?? szeggel";
			comment = "az oszlop tengely??nek ir??nya az oszlop k??zep??t??l " + pathDistance + " m??terre " +
					(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "el??re" : "h??tra")
					+ " (az oszlop tengelye a nyomvonallal " + ((180 - rotation) / 2) + "??-os sz??get z??r be)";
			break;
		case "2":
			id = 2;
			sign = "kar?? szeggel";
			comment = "az oszlop tengely??re mer??leges tengely ir??nya az oszlop k??zep??t??l " + pathDistance + " m??terre " +
					(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "jobbra" : "balra")
					+ "(a mer??leges tengely a nyomvonallal " + (90 - (180 - rotation) / 2) + "??-os sz??get z??r be)";
			break;
		case "3":
			id = 3;
			sign = "kar?? szeggel";
			comment = "az oszlop tengely??nek ir??nya az oszlop k??zep??t??l " + pathDistance + " m??terre " +
					(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "h??tra" : "el??re")
					+ " (az oszlop tengelye a nyomvonallal " + ((180 - rotation) / 2) + "??-os sz??get z??r be)";
			break;
		case "4":
			id = 4;
			sign = "kar?? szeggel";
			comment = "az oszlop tengely??re mer??leges tengely ir??nya az oszlop k??zep??t??l " + pathDistance + " m??terre " +
					(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "balra" : "jobbra")
					+ " (a mer??leges tengely a nyomvonallal " + (90 - (180 - rotation) / 2) + "??-os sz??get z??r be)";
			break;
		case "5":
			id = 5;
			sign = "kar?? szeggel";
			comment = "az oszlop tengely??nek ir??nya a g??dr??k sz??lein??l " +
					(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "el??re" : "h??tra")
					+ " (az oszlop tengelye a nyomvonallal " + ((180 - rotation) / 2) + "??-os sz??get z??r be)";
			break;
		case "6":
			id = 6;
			sign = "kar?? szeggel";
			comment = "az oszlop tengely??re mer??leges tengely ir??nya a g??dr??k sz??lein??l " +
					(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "jobbra" : "balra")
					+ " (a mer??leges tengely a nyomvonallal " + (90 - (180 - rotation) / 2) + "??-os sz??get z??r be)";
			break;
		case "7":
			id = 7;
			sign = "kar?? szeggel";
			comment = "az oszlop tengely??nek ir??nya a g??dr??k sz??lein??l " +
					(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "h??tra" : "el??re")
					+ " (az oszlop tengelye a nyomvonallal " + ((180 - rotation) / 2) + "??-os sz??get z??r be)";
			break;
		case "8":
			id = 8;
			sign = "kar?? szeggel";
			comment = "az oszlop tengely??re mer??leges tengely ir??nya a g??dr??k sz??lein??l " +
					(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "balra" : "jobbra")
					+ " (a mer??leges tengely a nyomvonallal " + (90 - (180 - rotation) / 2) + "??-os sz??get z??r be)";
			break;
		case "9":
			id = 9;
			sign = "kar??";
			comment ="az oszlop l??b g??d??r sz??le";
			break;
		case "10":
			id = 10;
			sign = "kar??";
			comment ="az oszlop l??b g??d??r sz??le";
			break;
		case "11":
			id = 11;
			sign = "kar??";
			comment ="az oszlop l??b g??d??r sz??le";
			break;
		case "12":
			id = 12;
			sign = "kar??";
			comment ="az oszlop l??b g??d??r sz??le";
			break;
		case "13":
			id = 13;
			sign = "kar??";
			comment ="az oszlop l??b g??d??r sz??le";
			break;
		case "14":
			id = 14;
			sign = "kar??";
			comment ="az oszlop l??b g??d??r sz??le";
			break;
		case "15":
			id = 15;
			sign = "kar??";
			comment ="az oszlop l??b g??d??r sz??le";
			break;
		case "16":
			id = 16;
			sign = "kar??";
			comment ="az oszlop l??b g??d??r sz??le";
			break;
		case "17":
			id = 17;
			sign = "kar??";
			comment ="az oszlop l??b g??d??r sz??le";
			break;
		case "18":
			id = 18;
			sign = "kar??";
			comment ="az oszlop l??b g??d??r sz??le";
			break;
		case "19":
			id = 19;
			sign = "kar??";
			comment ="az oszlop l??b g??d??r sz??le";
			break;
		case "20":
			id = 20;
			sign = "kar??";
			comment ="az oszlop l??b g??d??r sz??le";
			break;
		case "21":
			id = 21;
			sign = "kar??";
			comment ="az oszlop l??b g??d??r sz??le";
			break;
		case "22":
			id = 22;
			sign = "kar??";
			comment ="az oszlop l??b g??d??r sz??le";
			break;
		case "23":
			id = 23;
			sign = "kar??";
			comment ="az oszlop l??b g??d??r sz??le";
			break;
		case "24":
			id = 24;
			sign = "kar??";
			comment ="az oszlop l??b g??d??r sz??le";
			break;
		case "25" :
			id = 25;
			sign = "kar??";
			comment = centerPoint.getPointID() + ". sz??m?? oszlop ir??nya 20 m??terre";
			break;
		case "26" :
			id = 26;
			sign = "kar??";
			if( getPointIntegerID(directionPoint) == 2 ) 
			comment = directionPoint.getPointID() + ". sz??m?? oszlop ir??nya 20 m??terre";
			else if(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint))
			comment  = (getPointIntegerID(centerPoint) - 1) + ". sz??m?? oszlop ir??nya 20 m??terre";
			else if (getPointIntegerID(centerPoint) > getPointIntegerID(directionPoint))
			comment  = (getPointIntegerID(centerPoint) + 1) + ". sz??m?? oszlop ir??nya 20 m??terre";		
		}
	}
	
	private void setMetaDataForRotatedPlateBaseControlledPoint() {
		
		String[] pointIDComponents = pointID.split("_");
		
		if( pointIDComponents.length == 1) {
			setCenterPoint();
			return;
		}
		
		switch (pointIDComponents[1]) {
		case "1":
			id = 1;
			sign = "kar??";
			comment = "az alap g??dr??nek pontja";
			break;
		case "2":
			id = 2;
			sign = "kar??";
			comment = "az alap g??dr??nek pontja";
			break;
		case "3":
			id = 3;
			sign = "kar??";
			comment = "az alap g??dr??nek pontja";
			break;
		case "4":
			id = 4;
			sign = "kar??";
			comment = "az alap g??dr??nek pontja";
			break;
		case "5":
			id = 5;
			sign = "kar?? szeggel";
			comment = "az oszlop tengely??re mer??leges tengely ir??nya az alap g??dr??nek sz??l??t??l " 
			+ verticalDistanceFromSideOfHole + " m??terre " + 
					(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "balra" : "jobbra")
					+ " (a mer??leges tengely a nyomvonallal " + (90  + (180 - rotation) / 2) + "??-os sz??get z??r be)";
			break;
		case "6":
			id = 6;
			sign = "kar?? szeggel";
			comment = "az oszlop tengely??nek ir??nya az alap g??dr??nek sz??l??t??l " 
			+ horizontalDistanceFromSideOfHole + " m??terre " + 
					(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "el??re" : "h??tra")
					+ " (az oszlop tengelye a nyomvonallal " + ((180 - rotation) / 2) + "??-os sz??get z??r be)";
			break;
		case "7":
			id = 7;
			sign = "kar?? szeggel";
			comment = "az oszlop tengely??re mer??leges tengely ir??nya az alap g??dr??nek sz??l??t??l " 
			+ verticalDistanceFromSideOfHole + " m??terre " + 
					(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "jobbra" : "balra")
					+ " (a mer??leges tengely a nyomvonallal " + (90 + (180 - rotation) / 2) + "??-os sz??get z??r be)";
			break;
		case "8":
			id = 8;
			sign = "kar?? szeggel";
			comment = "az oszlop tengely??nek ir??nya az alap g??dr??nek sz??l??t??l " 
			+ horizontalDistanceFromSideOfHole + " m??terre " + 
					(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "h??tra" : "el??re")
					+ " (az oszlop tengelye a nyomvonallal " + ((180 - rotation) / 2) + "??-os sz??get z??r be)";
			break;
		case "9":
			id = 9;
			sign = "kar??";
			comment = getPointIntegerID(centerPoint) + ". sz??m?? oszlop ir??nya 20 m??terre";
			break;
		case "10":
			id = 10;
			sign = "kar??";
			if( getPointIntegerID(directionPoint) == 2 ) 
			comment = directionPoint.getPointID() + ". sz??m?? oszlop ir??nya 20 m??terre";
			else if(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint))
			comment  = (getPointIntegerID(centerPoint) - 1) + ". sz??m?? oszlop ir??nya 20 m??terre";
			else if (getPointIntegerID(centerPoint) > getPointIntegerID(directionPoint))
			comment  = (getPointIntegerID(centerPoint) + 1) + ". sz??m?? oszlop ir??nya 20 m??terre";	
		}
		
	}
	
	private void setCenterPoint() {
		id = 0;
		sign = "kar?? szeggel";
		comment = "az oszlop k??zepe";
	}

	@Override
	public int compareTo(SteakoutedCoords o) {
		return this.id > o.id ?  1 : this.id == o.id ? 0 : - 1;
	}
}
 