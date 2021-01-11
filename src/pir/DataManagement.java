package pir;

import java.util.ArrayList;
import java.util.List;

public class DataManagement {
	
	private static final int TIME = 0;
	private static final int ID = 1;
	private static final int SS = 2;
	private static final int DISTANCE = 3;
	private static final int COMPASS = 4;
	
	public static List<double[]> findWifiLandmarks(double[][] data){
		List<double[]> result = new ArrayList<double[]>();
		int size = data[0].length;
		int xSinceLast = 0;
		int ySinceLast = 0;
		for (int i = 1; i < data.length-1; i++) {
			xSinceLast += data[i][DISTANCE]*Math.sin(data[i][COMPASS]*Math.PI/180);
			ySinceLast -= data[i][DISTANCE]*Math.cos(data[i][COMPASS]*Math.PI/180);
			if(data[i-1][SS]<data[i][SS] && data[i][SS] > data[i+1][SS] && ((data[i][COMPASS]<(data[i-1][COMPASS]+180-20)%360) || (data[i][COMPASS]>(data[i-1][COMPASS]+180+20)%360))) {
				double[] temp = new double[size+2];
				for (int j = 0; j < size; j++) {
					temp[j] = data[i][j];
				}
				temp[size] = xSinceLast;
				temp[size+1] = ySinceLast;
				result.add(temp);
				xSinceLast = 0;
				ySinceLast = 0;
			}
		}
		result.get(0)[size] = 0;
		result.get(0)[size+1] = 0;
		return result;
	}
	
	public static List<double[]> createWifiLandmarksList(List<double[]> recucedTrajectory){
		List<double[]> result = new ArrayList<double[]>();
		result.add(new double[] {recucedTrajectory.get(0)[ID],recucedTrajectory.get(0)[5],recucedTrajectory.get(0)[6],1});
		int j;
		for (int i = 1; i < recucedTrajectory.size(); i++) {
			boolean cond = false;
			for (j = 0; j < result.size(); j++) {
				if (result.get(j)[0]==recucedTrajectory.get(i)[ID]) {
					cond = true;
					break;
				}
			}
			if (cond) {
				result.get(j)[1] = (result.get(j)[1]*result.get(j)[3] + recucedTrajectory.get(i)[5])/(result.get(j)[3]+1);
				result.get(j)[2] = (result.get(j)[2]*result.get(j)[3] + recucedTrajectory.get(i)[6])/(result.get(j)[3]+1);
				result.get(j)[3] = result.get(j)[3] + 1;
			} else {
				double[] tmp = new double[4];
				tmp[0] = recucedTrajectory.get(i)[ID];
				tmp[1] = recucedTrajectory.get(i)[5];
				tmp[2] = recucedTrajectory.get(i)[6];
				tmp[3] = 1;
				result.add(tmp);
			}
		}
		
		return result;
	}
	
	public static List<double[]> updateWifiLandmarksList(List<double[]> recucedTrajectory, List<double[]> existing){
		int j;
		double xInit = 0;
		double yInit = 0;
		boolean cont = false;
		int ex;
		for (ex = 0; ex < recucedTrajectory.size(); ex++) {
			for (int k = 0; k < existing.size(); k++) {
				if (existing.get(k)[0]==recucedTrajectory.get(ex)[ID]) {
					cont = true;
					xInit = existing.get(k)[1] - recucedTrajectory.get(ex)[5];
					yInit = existing.get(k)[2] - recucedTrajectory.get(ex)[6];
					System.out.println("init = "+xInit + " , " +yInit);
					break;
				}
			}
			if (cont) {
				break;
			}
		}
		if (!cont) {
			return existing;
		}
		for (int i = 0; i < recucedTrajectory.size(); i++) {
			boolean cond = false;
			if (i != ex) {
				for (j = 0; j < existing.size(); j++) {
					if (existing.get(j)[0]==recucedTrajectory.get(i)[ID]) {
						cond = true;
						break;
					}
				}
				if (cond) {
					existing.get(j)[1] = (existing.get(j)[1]*existing.get(j)[3] + recucedTrajectory.get(i)[5] + xInit)/(existing.get(j)[3]+1);
					existing.get(j)[2] = (existing.get(j)[2]*existing.get(j)[3] + recucedTrajectory.get(i)[6] + yInit)/(existing.get(j)[3]+1);
					existing.get(j)[3] = existing.get(j)[3] + 1;
				} else {
					double[] tmp = new double[4];
					tmp[0] = recucedTrajectory.get(i)[ID];
					tmp[1] = recucedTrajectory.get(i)[5] + xInit;
					tmp[2] = recucedTrajectory.get(i)[6] + yInit;
					tmp[3] = 1;
					existing.add(tmp);
				}
			}
		}
		
		return existing;
	}
	
	public static List<double[]> relativeTrajectory(List<double[]> reducedTrajectory, double[][] trajectory){
		List<double[]> relativeTrajectory = new ArrayList<double[]>();
		int last = 0;
		int begin = 0;
		for (begin = 0; begin < trajectory.length; begin++) {
			if (reducedTrajectory.get(last)[TIME]==trajectory[begin][TIME]) {
				break;
			}
		}
		for (int i = 1; i < reducedTrajectory.size(); i++) {
			int end = 0;
			int oldD = 0;
			for (end = begin+1; end < trajectory.length; end++) {
				oldD += trajectory[end][DISTANCE];
				if (reducedTrajectory.get(i)[TIME]==trajectory[end][TIME]) {
					break;
				}
			}
			int d = 0;
			int j;
			double oldCompass;
			boolean cond = false;
			System.out.println("begin: " + begin + "end: " +  end);
			d += trajectory[begin+1][DISTANCE];
			oldCompass = trajectory[begin+1][COMPASS];
			//maybe average compass
			for ( j = begin+2; j <= end; j++) {
				
				if (oldCompass < 10) {
					if (trajectory[j][COMPASS]>=360+oldCompass-10 || trajectory[j][COMPASS] < oldCompass + 10) {
						cond = true;
					}
				} else if (oldCompass > 350) {
					if (trajectory[j][COMPASS]>oldCompass-10 || trajectory[j][COMPASS]>(oldCompass+10)%360) {
						cond = true;
					}
				} else {
					if (trajectory[j][COMPASS]>oldCompass-10 && trajectory[j][COMPASS]<oldCompass+10) {
						cond = true;
					}
				}
				if (!(cond)) {
					if (reducedTrajectory.get(last)[ID] == reducedTrajectory.get(i)[ID]) {
						relativeTrajectory.add(new double[] {trajectory[j-1][TIME],reducedTrajectory.get(last)[ID],reducedTrajectory.get(i)[ID], d,oldCompass});
					} else {
						relativeTrajectory.add(new double[] {trajectory[j-1][TIME],reducedTrajectory.get(last)[ID],reducedTrajectory.get(i)[ID], d/oldD,oldCompass});
					}
					d = 0;
					oldCompass = trajectory[j][COMPASS];
					cond = false;
				}
				d+=trajectory[j][DISTANCE];
			}
			if (reducedTrajectory.get(last)[ID] == reducedTrajectory.get(i)[ID]) {
				relativeTrajectory.add(new double[] {trajectory[end][TIME],reducedTrajectory.get(last)[ID],reducedTrajectory.get(i)[ID], d,trajectory[end][COMPASS]});
			} else {
				relativeTrajectory.add(new double[] {trajectory[end][TIME],reducedTrajectory.get(last)[ID],reducedTrajectory.get(i)[ID], -1,trajectory[end][COMPASS]});
			}
			
			last = i;
			begin = end;
			oldD = 0;
		}
		return relativeTrajectory;
	}
}