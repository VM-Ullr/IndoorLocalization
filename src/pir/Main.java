package pir;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		double[][] data = new double[69][5];
		List<double[]> result = new ArrayList<double[]>();
		//0 to 1
		for (int i = 0; i < 13; i++) {
			data[0+i] =(new double[]{0+i,1,1+i,1,0});
		}
		//1 to 2
		for (int i = 1; i < 7; i++) {
			data[12+i] =(new double[]{12+i,1,13-i,1,0});
		}
		for (int i = 1; i < 7; i++) {
			data[18+i] =(new double[]{18+i,2,7+i,1,0});
		}
		//2 to 3
		for (int i = 1; i < 5; i++) {
			data[24+i] =(new double[]{24+i,2,13-i,1,90});
		}
		for (int i = 1; i < 6; i++) {
			data[28+i] =(new double[]{28+i,3,9+i,1,90});
		}
		//3 to 4
		for (int i = 1; i < 5; i++) {
			data[33+i] =(new double[]{33+i,3,14-i,1,90});
		}
		for (int i = 1; i < 5; i++) {
			data[37+i] =(new double[]{37+i,4,10+i,1,90});
		}
		//4 to 5
		data[42] =(new double[]{42,4,13,1,90});
		for (int i = 1; i < 3; i++) {
			data[42+i] =(new double[]{42+i,5,13+i,1,90});
		}
		//5 to 6
		for (int i = 1; i < 5; i++) {
			data[44+i] =(new double[]{44+i,5,15-i,1,90});
		}
		for (int i = 1; i < 5; i++) {
			data[48+i] =(new double[]{48+i,6,11+i,1,90});
		}
		//6 to 7
		for (int i = 1; i < 5; i++) {
			data[52+i] =(new double[]{52+i,6,15-i,1,0});
		}
		for (int i = 1; i < 5; i++) {
			data[56+i] =(new double[]{56+i,7,11+i,1,0});
		}
		//7 to 7 U turn
		for (int i = 1; i < 3; i++) {
			data[60+i] =(new double[]{60+i,7,15-i,1,0});
		}
		//to continue
		
		
		result = DataManagement.findWifiLandmarks(data);		
		double x = 400;
		double y = 300+130;
		Canvas canvasInit = new Canvas();
		for (int i = 0; i < data.length; i++) {
			x += data[i][3]*Math.sin(data[i][4]*Math.PI/180)*10;
			y -= data[i][3]*Math.cos(data[i][4]*Math.PI/180)*10;
			if (i == 12 || i == 24 || i == 33 || i == 41 || i == 44 || i == 52 || i == 60) {
				canvasInit.addPoint(x, y,Color.decode("#eb5600"));
			}else{
				canvasInit.addPoint(x, y);
			}
		}
		
		x=400;
		y=300;
		Canvas canvas = new Canvas();
		for (int i = 0; i < result.size(); i++) {
			for (int j = 0; j < result.get(i).length; j++) {
				System.out.print(result.get(i)[j] + ", ");
			}
			System.out.println("");
			x+= result.get(i)[5]*10;
			y+= result.get(i)[6]*10;
			canvas.addPoint(x,y,Color.decode("#eb5600"));
		}
		
		List<double[]> resultTest = new ArrayList<double[]>();
		List<double[]> imputTest = new ArrayList<double[]>();
		imputTest.add(new double[]{0,1,0,0,0,0,0});
		imputTest.add(new double[]{0,2,0,0,0,12,6});
		imputTest.add(new double[]{0,3,0,0,0,-20,9});
		imputTest.add(new double[]{0,4,0,0,0,-13,-8});
		imputTest.add(new double[]{0,2,0,0,0,9,8});
		resultTest = DataManagement.createWifiLandmarksList(imputTest);
		System.out.println("TEST 1");
		for (int i = 0; i < resultTest.size(); i++) {
			for (int j = 0; j < resultTest.get(i).length; j++) {
				System.out.print(resultTest.get(i)[j] + ", ");
			}
			System.out.println("");
		}
		List<double[]> imputTest2 = new ArrayList<double[]>();
		imputTest2.add(new double[]{0,1,0,0,0,1,2});
		imputTest2.add(new double[]{0,2,0,0,0,10,7});
		imputTest2.add(new double[]{0,3,0,0,0,-25,4});
		imputTest2.add(new double[]{0,4,0,0,0,-13,-3});
		imputTest2.add(new double[]{0,4,0,0,0,-15,-8});
		imputTest2.add(new double[]{0,3,0,0,0,-18,6});
		imputTest2.add(new double[]{0,1,0,0,0,-1,1});
		
		x=400;
		y=300;
		Canvas canvasBeforeCluster = new Canvas();
		for (int i = 0; i < imputTest.size(); i++) {
			switch ((int)imputTest.get(i)[1]) {
			case 1:
				canvasBeforeCluster.addPoint(x+imputTest.get(i)[5]*10,y+imputTest.get(i)[6]*10,Color.black);
				break;
			case 2:
				canvasBeforeCluster.addPoint(x+imputTest.get(i)[5]*10,y+imputTest.get(i)[6]*10,Color.decode("#1A9988"));
				break;
			case 3:
				canvasBeforeCluster.addPoint(x+imputTest.get(i)[5]*10,y+imputTest.get(i)[6]*10,Color.decode("#eb5600"));
				break;

			default:
				canvasBeforeCluster.addPoint(x+imputTest.get(i)[5]*10,y+imputTest.get(i)[6]*10,Color.blue);
				break;
			}
			
		}
		
		for (int i = 0; i < imputTest2.size(); i++) {
			switch ((int)imputTest2.get(i)[1]) {
			case 1:
				canvasBeforeCluster.addPoint(x+imputTest2.get(i)[5]*10-10,y+imputTest2.get(i)[6]*10-20,Color.black);
				break;
			case 2:
				canvasBeforeCluster.addPoint(x+imputTest2.get(i)[5]*10-10,y+imputTest2.get(i)[6]*10-20,Color.decode("#1A9988"));
				break;
			case 3:
				canvasBeforeCluster.addPoint(x+imputTest2.get(i)[5]*10-10,y+imputTest2.get(i)[6]*10-20,Color.decode("#eb5600"));
				break;

			default:
				canvasBeforeCluster.addPoint(x+imputTest2.get(i)[5]*10-10,y+imputTest2.get(i)[6]*10-20,Color.blue);
				break;
			}
			
		}
		
		
		
		resultTest = DataManagement.updateWifiLandmarksList(imputTest2,resultTest);
		System.out.println("TEST 2");
		for (int i = 0; i < resultTest.size(); i++) {
			for (int j = 0; j < resultTest.get(i).length; j++) {
				System.out.print(resultTest.get(i)[j] + ", ");
			}
			System.out.println("");
		}
		
		Canvas canvasAfterCluster = new Canvas();
		for (int i = 0; i < resultTest.size(); i++) {
			switch ((int)resultTest.get(i)[0]) {
			case 1:
				canvasAfterCluster.addPoint(x+resultTest.get(i)[1]*10,y+resultTest.get(i)[2]*10,Color.black);
				break;
			case 2:
				canvasAfterCluster.addPoint(x+resultTest.get(i)[1]*10,y+resultTest.get(i)[2]*10,Color.decode("#1A9988"));
				break;
			case 3:
				canvasAfterCluster.addPoint(x+resultTest.get(i)[1]*10,y+resultTest.get(i)[2]*10,Color.decode("#eb5600"));
				break;

			default:
				canvasAfterCluster.addPoint(x+resultTest.get(i)[1]*10,y+resultTest.get(i)[2]*10,Color.blue);
				break;
			}
			
		}
		
		JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(canvasInit);
        f.setSize(800,600);
        f.setLocation(200,200);
        f.setVisible(true);
        
        JFrame f2 = new JFrame();
        f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f2.getContentPane().add(canvas);
        f2.setSize(800,600);
        f2.setLocation(200+800,200);
        f2.setVisible(true);
        
        List<List<double[]>> relativTest = new ArrayList<List<double[]>>();
        relativTest.add(DataManagement.relativeTrajectory(result, data));
        System.out.println("FINAL TEST");
        for (int i = 0; i < relativTest.get(0).size(); i++) {
			System.out.println(relativTest.get(0).get(i)[0] + " " +relativTest.get(0).get(i)[1] + " " +relativTest.get(0).get(i)[2] + " " +relativTest.get(0).get(i)[3] + " " +relativTest.get(0).get(i)[4]);
		}
        
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter pour continuer: ");
        int n = reader.nextInt(); 
        System.out.println("ok");
        f.getContentPane().removeAll();
        f.getContentPane().add(canvasBeforeCluster);
        f2.getContentPane().removeAll();
        f2.getContentPane().add(canvasAfterCluster);
        f.setVisible(true);
        f2.setVisible(true);
        
        reader.close();
        
	}

}