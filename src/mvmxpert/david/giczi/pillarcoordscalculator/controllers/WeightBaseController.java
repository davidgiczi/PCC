package mvmxpert.david.giczi.pillarcoordscalculator.controllers;

import javax.management.InvalidAttributeValueException;
import javax.swing.JOptionPane;

import mvmxpert.david.giczi.pillarcoordscalculator.fileprocess.FileProcess;
import mvmxpert.david.giczi.pillarcoordscalculator.service.InputDataValidator;
import mvmxpert.david.giczi.pillarcoordscalculator.service.PillarCoordsForWeightBase;
import mvmxpert.david.giczi.pillarcoordscalculator.service.Point;
import mvmxpert.david.giczi.pillarcoordscalculator.view.WeightBaseDisplayer;

public class WeightBaseController implements Controller {

	private HomeController homeController;
	private String centerID;
	private String directionID;
	private double centerX;
	private double centerY;
	private double directionX;
	private double directionY;
	private double distanceOnTheAxis;
	private double horizontalDistanceBetweenPillarLegs;
	private double verticalDistanceBetweenPillarLegs;
	private double horizontalSizeOfHoleOfPillarLeg;
	private double verticalSizeOfHoleOfPillarLeg;
	private double rotationAngle;
	private double rotationMin;
	private double rotationSec;
	private boolean nonValidProjectName;
	
	public WeightBaseController(HomeController homeController) {
		this.homeController = homeController;
	}
	
	@Override
	public void handleCountButtonClick() {
		
		if( !isValidInputID() ) {
			return;
		}
		try {
			isValidInputData();
			Point center = new Point(centerID, centerX, centerY);
			Point direction = new Point(directionID, directionX, directionY);
			homeController.weightBaseCoordsCalculator = new PillarCoordsForWeightBase(center, direction);
			homeController.weightBaseCoordsCalculator.setDistanceOnTheAxis(distanceOnTheAxis);
			homeController.weightBaseCoordsCalculator.setHorizontalDistanceBetweenPillarLegs(horizontalDistanceBetweenPillarLegs);
			homeController.weightBaseCoordsCalculator.setVerticalDistanceBetweenPillarLegs(verticalDistanceBetweenPillarLegs);
			homeController.weightBaseCoordsCalculator.setHorizontalSizeOfHoleOfPillarLeg(horizontalSizeOfHoleOfPillarLeg);
			homeController.weightBaseCoordsCalculator.setVerticalSizeOfHoleOfPillarLeg(verticalSizeOfHoleOfPillarLeg);
			homeController.weightBaseCoordsCalculator.setAngleValueBetweenMainPath(rotationAngle);
			homeController.weightBaseCoordsCalculator.setAngularMinuteValueBetweenMainPath(rotationMin);
			homeController.weightBaseCoordsCalculator.setAngularSecondValueBetweenMainPath(rotationSec);
			homeController.weightBaseCoordsCalculator.calculatePillarPoints();
			 if ( saveAsProject() ) {
				 createProjectFile(centerID, centerX, centerY, 
					  directionID, directionX, directionY,
					  distanceOnTheAxis, 
					  horizontalDistanceBetweenPillarLegs, 
					  verticalDistanceBetweenPillarLegs, 
					  horizontalSizeOfHoleOfPillarLeg, 
					  verticalSizeOfHoleOfPillarLeg, 
					  rotationAngle, rotationSec, rotationMin);
			 }
			 if( nonValidProjectName ) {
				 nonValidProjectName = false;
				 return;
			 }
			saveCoordFiles();
			homeController.weightBaseDisplayer = new WeightBaseDisplayer
					(homeController.weightBaseCoordsCalculator.getPillarPoints(), 
					 homeController.weightBaseCoordsCalculator.getAxisDirectionPoint(),
					 homeController.weightBaseCoordsCalculator.getRadRotation(),
					   FileProcess.FOLDER_PATH + "\\" + HomeController.PROJECT_NAME + ".pcc");
			setVisible();
			destroy();
		} catch (InvalidAttributeValueException e) {
			HomeController.getInfoMessage("Bemeneti adatok megad??sa",
					"Az oszlopok megadott koordin??t??i alapj??n ir??nysz??g nem sz??m??that??.");
		}
		  catch (NumberFormatException e) {
			HomeController.getInfoMessage("Bemeneti adatok megad??sa",
					"Minden ??res adatmez?? kit??lt??se ??s sz??m ??rt??k megad??sa sz??ks??ges.");	
			}
		}
	
	@Override
	public boolean saveAsProject() {
		
		if( FileProcess.isProjectFileExist() ) {
			
			if( homeController.getWarningMessage("\"" + HomeController.PROJECT_NAME + ".pcc\"", 
					"L??tez?? " + homeController.getBaseType() + " projekt f??jl, biztos, hogy fel??l??rod?") == 2 ) {
				String newProjectName = createNewProject();
				if(newProjectName == null) 
					return false;
				if( !InputDataValidator.isValidProjectName(newProjectName) ) {
					nonValidProjectName = true;
					return false;
				}
		}
	}
		return true;
}
	
	@Override
	public void setVisible() {
		homeController.homeWindow.baseDataMenu.setEnabled(true);
		homeController.homeWindow.controlSteakoutMenu.setEnabled(true);
		homeController.weightBaseInputWindow.inputFrameForWeightBase.setVisible(false);
		if(homeController.plateBaseDisplayer != null ) {
			homeController.plateBaseDisplayer.setVisible(false);
		}
	}
	
	@Override
	public String createNewProject() {
		String projectName = 
				JOptionPane.showInputDialog(null, "Add meg a projekt nev??t:", "A projekt nev??nek megad??sa", JOptionPane.DEFAULT_OPTION);
		if( projectName != null && InputDataValidator.isValidProjectName(projectName) ) {
			FileProcess.setFolder();
			if( FileProcess.FOLDER_PATH != null ) {
			HomeController.PROJECT_NAME = projectName;
			homeController.getExistedProjectInfoMessage();
			homeController.weightBaseInputWindow.inputFrameForWeightBase.setTitle(HomeController.PROJECT_NAME);
		}
	}
		else if( projectName != null && !InputDataValidator.isValidProjectName(projectName) ) {
		HomeController.getInfoMessage("Projekt n??v megad??sa", "A projekt neve legal??bb 3 karakter hossz??s??g?? ??s bet??vel kezd??d?? lehet.");
		}
		
		return projectName;
	}
	
	@Override
	public void saveCoordFiles() {
		
		if(homeController.weightBaseInputWindow.all.isSelected() ) {
			FileProcess.saveDataForKML(homeController.weightBaseCoordsCalculator.getPillarCenterPoint(), 
					homeController.weightBaseCoordsCalculator.getAxisDirectionPoint());
			FileProcess.saveDataForRTK(homeController.weightBaseCoordsCalculator.getPillarPoints(), 
					homeController.weightBaseCoordsCalculator.getAxisDirectionPoint());
			FileProcess.saveDataForTPS(homeController.weightBaseCoordsCalculator.getPillarPoints(), 
					homeController.weightBaseCoordsCalculator.getAxisDirectionPoint());
			FileProcess.saveDataForMS(homeController.weightBaseCoordsCalculator.getPillarPoints(), 
					homeController.weightBaseCoordsCalculator.getAxisDirectionPoint());
		}
		if( homeController.weightBaseInputWindow.kml.isSelected() ) {
			FileProcess.saveDataForKML(homeController.weightBaseCoordsCalculator.getPillarCenterPoint(), 
					homeController.weightBaseCoordsCalculator.getAxisDirectionPoint());
		}
		if( homeController.weightBaseInputWindow.rtk.isSelected() ) {
			FileProcess.saveDataForRTK(homeController.weightBaseCoordsCalculator.getPillarPoints(), 
					homeController.weightBaseCoordsCalculator.getAxisDirectionPoint());
		}
		if( homeController.weightBaseInputWindow.tps.isSelected() ) {
			FileProcess.saveDataForTPS(homeController.weightBaseCoordsCalculator.getPillarPoints(), 
					homeController.weightBaseCoordsCalculator.getAxisDirectionPoint());
		}
		if( homeController.weightBaseInputWindow.ms.isSelected() ) {
			FileProcess.saveDataForMS(homeController.weightBaseCoordsCalculator.getPillarPoints(),
					homeController.weightBaseCoordsCalculator.getAxisDirectionPoint());
		}
	}
	
	@Override
	public void destroy() {
		homeController.plateBaseCoordsCalculator = null;
		homeController.plateBaseDisplayer = null;
	}

	private boolean isValidInputID() {
			
			String centerID = homeController.weightBaseInputWindow.centerIdField.getText();
			String directionID = homeController.weightBaseInputWindow.directionIdField.getText();
			 
			if( !InputDataValidator.isValidID(centerID) || !InputDataValidator.isValidID(directionID) ) {
				HomeController.getInfoMessage("Az oszlopok nev??nek megad??sa",
						"Az oszlopok neve/sz??ma legal??bb egy karakter hossz??s??g?? legyen.");
				return false;
		}
				this.centerID = centerID;
				this.directionID = directionID;
				return true;
		}
	
		private void isValidInputData() throws NumberFormatException {
			centerX = InputDataValidator
					.isValidInputNumberValue(homeController.weightBaseInputWindow.x_centerField.getText().replace(',' , '.'));
			centerY = InputDataValidator
					.isValidInputNumberValue(homeController.weightBaseInputWindow.y_centerField.getText().replace(',', '.'));
			directionX = InputDataValidator
					.isValidInputNumberValue(homeController.weightBaseInputWindow.x_directionField.getText().replace(',', '.'));
			directionY = InputDataValidator
					.isValidInputNumberValue(homeController.weightBaseInputWindow.y_directionField.getText().replace(',', '.'));
			distanceOnTheAxis = InputDataValidator
					.isValidInputNumberValue(homeController.weightBaseInputWindow.directionSizeField.getText().replace(',', '.'));
			horizontalDistanceBetweenPillarLegs = InputDataValidator
					.isValidInputNumberValue(homeController.weightBaseInputWindow.horizontalSizeForPillarLegField.getText().replace(',', '.'));
			verticalDistanceBetweenPillarLegs = InputDataValidator
					.isValidInputNumberValue(homeController.weightBaseInputWindow.verticalSizeForPillarLegField.getText().replace(',', '.'));
			horizontalSizeOfHoleOfPillarLeg = InputDataValidator
					.isValidInputNumberValue(homeController.weightBaseInputWindow.horizontalSizeForHoleField.getText().replace(',', '.'));
			verticalSizeOfHoleOfPillarLeg = InputDataValidator
					.isValidInputNumberValue(homeController.weightBaseInputWindow.verticalSizeForHoleField.getText().replace(',', '.'));
			rotationAngle = InputDataValidator.isValidAngleValue(homeController.weightBaseInputWindow.rotateAngularField.getText());
			rotationMin = InputDataValidator.isValidAngleValue(homeController.weightBaseInputWindow.rotateAngularMinField.getText());
			rotationSec = InputDataValidator.isValidAngleValue(homeController.weightBaseInputWindow.rotateAngularSecField.getText());
		}
		
	private void createProjectFile
		(String centerID, double centerX, double centerY, 
		 String directionID, double directionX,  double directionY,
		 double distanceOnTheAxis, 
		 double horizontalDistanceBetweenPillarLegs,
		 double verticalDistanceBetweenPillarLegs, 
		 double horizontalSizeOfHoleOfPillarLeg,
		 double verticalSizeOfHoleOfPillarLeg,
		 double rotationAngle, double rotationSec, double rotationMin) {
		
		FileProcess.saveProjectFileForWeightBase
		(centerID, centerX, centerY, 
		 directionID, directionX, directionY, 
		 distanceOnTheAxis, 
		 horizontalDistanceBetweenPillarLegs, 
		 verticalDistanceBetweenPillarLegs, 
		 horizontalSizeOfHoleOfPillarLeg, 
		 verticalSizeOfHoleOfPillarLeg, 
		 rotationAngle, rotationSec, rotationMin);
	}
		
}
