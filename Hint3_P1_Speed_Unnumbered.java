package org.optaplanner.examples.taskassigning.persistence;

import javax.swing.*;
import javax.swing.event.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.net.URL;
import java.applet.Applet;
import java.applet.AudioClip;
//2007W1084U9L6FBDTG25
public class Hint3_P1_Speed_Unnumbered extends JFrame {

	Graphics2D g2D;

	int widthFrame, heightFrame; // 以JFrame.getSize()取得的,用以設定中心點!

	boolean gameStart = false, brickToSite = false, brickInSite = false,
			siteBrickOut = false, brickToSiteL = false, brickInSiteL = false,
			siteBrickOutL = false, freeze, newBrickIncreFlag, threeBlock, freezeL,
			newBrickIncreFlagL, threeBlockL, overR = false, overL = false,
			rotateSpeedZero, rotateState = false, rotateClockwise = false,
			nowRPlayer = true, setBrickNotInput = false, setBrickNotInputL = false,
			RPlayerChangLPlayer = false, LPlayerChangRPlayer = false,
			RBrickCanNotChange = false, LBrickCanNotChange = false;

	Boolean testCentrifugal = false, testCentrifugalL = false;// for centrifugal

	int centrifugalValue = 3;

	int zeroTimes = 0, tempRotate;

	int siteBrickType, siteBrickSubType, siteBrickTypeL, siteBrickSubTypeL;

	int rotateAngel, rotateIndex, rotateAngelL, rotateIndexL, fallingBrickL,
			brickFallingNumber = 0;

	int numDeleted, newBrickIncre, numDeletedL, newBrickIncreL;

	int freezeTime = 2000, fallingSuspendTime = 500, newBrickTime = 200,
			testTime = 100;

	int outerX = 10, outerY = 30, outerR = 300, lengthBrick = 19,
			degreeBrick = 5, numTrack = 10, numTrack1 = 14,
			numLine = (360 / (2 * degreeBrick)) - 1, innerR = outerR - numTrack1
					* lengthBrick, centerX = outerX + outerR, centerY = outerY + outerR,
			length = lengthBrick * numTrack1;// length好像沒用到

	final int cordRadiusMax = numTrack - 1,
			cordAngelMax = (360 / degreeBrick) - 1,
			numAngelMax = (360 / degreeBrick);

	// 設定遊戲區域的範圍.
	int startAngelRPlayer = 8,
			endAngelRPlayer = (int) ((cordAngelMax + 1) / 2 - startAngelRPlayer),
			startAngelLPlayer = 8, // ****
			endAngelLPlayer = (int) ((cordAngelMax + 1) / 2 - startAngelLPlayer);

	int mapGame[][] = new int[numAngelMax][numTrack]; // 0(沒有方塊)__

	int mapGameL[][] = new int[numAngelMax][numTrack];// **** -10(有方塊)__-20(要消除方塊)

	boolean clockwise = true;// clockwise

	int bricksType[][][][] = {
			{ { { 0, 0 }, { 0, -1 }, { -1, -1 }, { 0, 1 } },
					{ { 0, 0 }, { -1, 0 }, { 1, 0 }, { 1, -1 } },
					{ { 0, 0 }, { 0, -1 }, { 0, 1 }, { 1, 1 } },
					{ { 0, 0 }, { -1, 0 }, { -1, 1 }, { 1, 0 } } },
			{ { { 0, 0 }, { 0, -1 }, { -1, 1 }, { 0, 1 } },
					{ { 0, 0 }, { -1, -1 }, { -1, 0 }, { 1, 0 } },
					{ { 0, 0 }, { 0, -1 }, { 0, 1 }, { 1, -1 } },
					{ { 0, 0 }, { -1, 0 }, { 1, 1 }, { 1, 0 } } },
			{ { { 0, 0 }, { 0, -1 }, { -1, 0 }, { 0, 1 } },
					{ { 0, 0 }, { 0, -1 }, { -1, 0 }, { 1, 0 } },
					{ { 0, 0 }, { 0, -1 }, { 0, 1 }, { 1, 0 } },
					{ { 0, 0 }, { -1, 0 }, { 0, 1 }, { 1, 0 } } },
			{ { { 0, 0 }, { 0, -1 }, { -1, 0 }, { -1, 1 } },
					{ { 0, 0 }, { 0, -1 }, { -1, -1 }, { 1, 0 } } },
			{ { { 0, 0 }, { -1, -1 }, { -1, 0 }, { 0, 1 } },
					{ { 0, 0 }, { 0, -1 }, { -1, 0 }, { 1, -1 } } },
			{ { { 0, 0 }, { -1, 0 }, { 1, 0 }, { 2, 0 } },
					{ { 0, 0 }, { 0, -1 }, { 0, -2 }, { 0, 1 } } },
			{ { { 0, 0 }, { 0, -1 }, { -1, -1 }, { -1, 0 } } } };

	int bricksFallingType[][][][] = {
			{ { { 1, 1 }, { 1, 0 }, { 1, -1 } }, { { 2, 0 }, { 2, -1 } },
					{ { 2, 1 }, { 1, 0 }, { 1, -1 } }, { { 2, 0 }, { 0, 1 } } },
			{ { { 1, 1 }, { 1, 0 }, { 1, -1 } }, { { 2, 0 }, { 0, -1 } },
					{ { 2, -1 }, { 1, 1 }, { 1, 0 } }, { { 2, 1 }, { 2, 0 } } },
			{ { { 1, 1 }, { 1, 0 }, { 1, -1 } }, { { 2, 0 }, { 1, -1 } },
					{ { 2, 0 }, { 1, -1 }, { 1, 1 } }, { { 2, 0 }, { 1, 1 } } },
			{ { { 1, 0 }, { 0, 1 }, { 1, -1 } }, { { 2, 0 }, { 1, -1 } } },
			{ { { 1, 1 }, { 1, 0 }, { 0, -1 } }, { { 2, -1 }, { 1, 0 } } },
			{ { { 3, 0 } }, { { 1, 1 }, { 1, 0 }, { 1, -1 }, { 1, -2 } } },
			{ { { 1, 0 }, { 1, -1 } } } };

	int bricksLeftType[][][][] = {
			{ { { 0, 2 }, { -1, 0 } }, { { -1, 1 }, { 0, 1 }, { 1, 1 } },
					{ { 0, 2 }, { 1, 2 } }, { { -1, 2 }, { 0, 1 }, { 1, 1 } } },
			{ { { -1, 2 }, { 0, 2 } }, { { -1, 1 }, { 0, 1 }, { 1, 1 } },
					{ { 0, 2 }, { 1, 0 } }, { { -1, 1 }, { 0, 1 }, { 1, 2 } } },
			{ { { 0, 2 }, { -1, 1 } }, { { -1, 1 }, { 0, 1 }, { 1, 1 } },
					{ { 0, 2 }, { 1, 1 } }, { { 0, 2 }, { -1, 1 }, { 1, 1 } } },
			{ { { -1, 2 }, { 0, 1 } }, { { 1, 1 }, { 0, 1 }, { -1, 0 } } },
			{ { { 0, 2 }, { -1, 1 } }, { { -1, 1 }, { 0, 1 } } },
			{ { { -1, 1 }, { 0, 1 }, { 1, 1 }, { 2, 1 } }, { { 0, 2 } } },
			{ { { -1, 1 }, { 0, 1 } } } };

	int bricksRightType[][][][] = {
			{ { { -1, -2 }, { 0, -2 } }, { { 1, -2 }, { -1, -1 }, { 0, -1 } },
					{ { 0, -2 }, { 1, 0 } }, { { -1, -1 }, { 0, -1 }, { 1, -1 } } },
			{ { { 0, -2 }, { -1, 0 } }, { { -1, -2 }, { 0, -1 }, { 1, -1 } },
					{ { 0, -2 }, { 1, -2 } }, { { -1, -1 }, { 0, -1 }, { 1, -1 } } },
			{ { { 0, -2 }, { -1, -1 } }, { { 0, -2 }, { -1, -1 }, { 1, -1 } },
					{ { 0, -2 }, { 1, -1 } }, { { -1, -1 }, { 0, -1 }, { 1, -1 } } },
			{ { { 0, -2 }, { -1, -1 } }, { { -1, -2 }, { 0, -2 }, { 1, -1 } } },
			{ { { -1, -2 }, { 0, -1 } }, { { 0, -2 }, { 1, -2 }, { -1, -1 } } },
			{ { { -1, -1 }, { 0, -1 }, { 1, -1 }, { 2, -1 } }, { { 0, -3 } } },
			{ { { -1, -2 }, { 0, -2 } } } };

	boolean fallingOver, fallingOverL;// ****

	int currentBrickAngel, currentBrickRadius, currentBrickAngelL,
			currentBrickRadiusL, testEndAngel;// ****

	Color colorJPanel = Color.white, colorTrack = Color.blue,
			nextBrickColor = Color.green;

	int currentBrickType = (int) (bricksType.length * Math.random()),
			currentBrickSubType, currentBrickRotateType,

			nextCurrentBrickType = (int) (bricksType.length * Math.random()),
			nextCurrentBrickSubType = 0,
			nextCurrentBrickTypeL = (int) (bricksType.length * Math.random()),
			nextCurrentBrickSubTypeL = 0,

			next2CurrentBrickType = (int) (bricksType.length * Math.random()),
			next2CurrentBrickSubType = 0,
			next2CurrentBrickTypeL = (int) (bricksType.length * Math.random()),
			next2CurrentBrickSubTypeL = 0,

			next3CurrentBrickType = (int) (bricksType.length * Math.random()),
			next3CurrentBrickSubType = 0,
			next3CurrentBrickTypeL = (int) (bricksType.length * Math.random()),
			next3CurrentBrickSubTypeL = 0,

			increAngel, increRadius, currentBrickTypeL, currentBrickSubTypeL,
			currentBrickRotateTypeL,// ****
			increAngelL, increRadiusL;// ****

	boolean isSpeedDownBrick = false, isSpeedDownBrickL = false,
			nextIsSpeedDownBrick = false, nextIsSpeedDownBrickL = false,
			next2IsSpeedDownBrick = false, next2IsSpeedDownBrickL = false,
			next3IsSpeedDownBrick = false, next3IsSpeedDownBrickL = false;

	int outerIncreLineXArray[] = new int[numLine + 1],
			outerIncreLineXArray1[] = new int[numLine + 1],
			outerIncreLineYArray[] = new int[numLine + 1],
			outerIncreLineYArray1[] = new int[numLine + 1],
			innerIncreLineXArray[] = new int[numLine + 1],
			innerIncreLineXArray1[] = new int[numLine + 1],
			innerIncreLineYArray[] = new int[numLine + 1],
			innerIncreLineYArray1[] = new int[numLine + 1],
			ovalCordX[] = new int[numTrack1 + 1],
			ovalCordY[] = new int[numTrack1 + 1],
			ovalCordL[] = new int[numTrack1 + 1],
			ovalCordW[] = new int[numTrack1 + 1];

	int siteouterIncreLineXArray[] = new int[5],
			siteouterIncreLineXArray1[] = new int[5],
			siteouterIncreLineYArray[] = new int[5],
			siteouterIncreLineYArray1[] = new int[5],
			siteinnerIncreLineXArray[] = new int[5],
			siteinnerIncreLineXArray1[] = new int[5],
			siteinnerIncreLineYArray[] = new int[5],
			siteinnerIncreLineYArray1[] = new int[5], siteovalCordX[] = new int[5],
			siteovalCordY[] = new int[5], siteovalCordL[] = new int[5],
			siteovalCordW[] = new int[5];

	int site2outerIncreLineXArray[] = new int[5],
			site2outerIncreLineXArray1[] = new int[5],
			site2outerIncreLineYArray[] = new int[5],
			site2outerIncreLineYArray1[] = new int[5],
			site2innerIncreLineXArray[] = new int[5],
			site2innerIncreLineXArray1[] = new int[5],
			site2innerIncreLineYArray[] = new int[5],
			site2innerIncreLineYArray1[] = new int[5], site2ovalCordX[] = new int[5],
			site2ovalCordY[] = new int[5], site2ovalCordL[] = new int[5],
			site2ovalCordW[] = new int[5];

	
	int XDecre[] = new int[numLine*2+2], YDecre[] = new int[numLine*2+2];
	int XArray[] = new int[numLine*2+2] , YArray[] = new int[numLine*2+2]; 
	double outerIncreLineXX, outerIncreLineYY;
	int XDecre1[][] = new int[numLine*2+2][4], YDecre1[][] = new int[numLine*2+2][4];
	int XArray1[] = new int[numLine*2+2] , YArray1[] = new int[numLine*2+2]; 
	double outerIncreLineXX1, outerIncreLineYY1;
	int XDecre2[][] = new int[numLine*2+2][2], YDecre2[][] = new int[numLine*2+2][2];
	int XArray2[] = new int[numLine*2+2] , YArray2[] = new int[numLine*2+2]; 
	double outerIncreLineXX2, outerIncreLineYY2;
	int XDecre3[][] = new int[numLine*2+2][3], YDecre3[][] = new int[numLine*2+2][3];
	int XArray3[] = new int[numLine*2+2] , YArray3[] = new int[numLine*2+2]; 
	double outerIncreLineXX3, outerIncreLineYY3;

	void setSchelton() {
		for (int i = 0; i <= numLine*2+1; i++) {
//		if(i != 36) continue;
//	for (int i = 0; i <= 0; i++) {				
		int tempR;
		if(i >= 0 && i <= 9)	tempR = outerR - (int)(lengthBrick/1);				
		else if(i >= 10 && i <= 13)	tempR = outerR - (int)(lengthBrick/1.1);
		else if(i >= 14 && i <= 16)	tempR = outerR - (int)(lengthBrick/1.2);
		else if(i >= 17 && i <= 18)	tempR = outerR - (int)(lengthBrick/1.5);
		else if(i >= 25 && i <= 27)	tempR = outerR - (int)(lengthBrick/2);
		else if(i >= 28 && i <= 30)	tempR = outerR - (int)(lengthBrick/3);				
		else if(i >= 31 && i <= 57)	tempR = outerR - (int)(lengthBrick/4);
		else if(i >= 58 && i <= 63) tempR = outerR - (int)(lengthBrick/2);
		else if(i >= 70 && i <= 71) tempR = outerR - (int)(lengthBrick/1.2);
		else tempR = outerR - (int)(lengthBrick/1.5);
		
		outerIncreLineXX = tempR * Math.cos((2 * Math.PI) * i * degreeBrick / 360);
		int Xdecre = (int)(lengthBrick * Math.cos((2 * Math.PI) * i * degreeBrick / 360));
		
		outerIncreLineYY = tempR	* Math.sin((2 * Math.PI) * i * degreeBrick / 360);
		int Ydecre = (int)(lengthBrick * Math.sin((2 * Math.PI) * i * degreeBrick / 360));				
		
		XArray[i] = (int) (centerX + outerIncreLineXX);
		YArray[i] = (int) (centerY - outerIncreLineYY);
	}
		for (int i = 0; i <= numLine*2+1; i++) {
//		if(i != 36) continue;
//	for (int i = 0; i <= 0; i++) {				
		int tempR;
		if(i >= 0 && i <= 5)	tempR = outerR - (int)(lengthBrick/1.5) - lengthBrick;
		else if(i >= 6 && i <= 9)	tempR = outerR - (int)(lengthBrick/1.3) - lengthBrick;				
		else if(i >= 10 && i <= 13)	tempR = outerR - (int)(lengthBrick/1.3) - lengthBrick;
		else if(i >= 14 && i <= 16)	tempR = outerR - (int)(lengthBrick/1.2) - lengthBrick;
		else if(i >= 17 && i <= 22)	tempR = outerR - (int)(lengthBrick/1.3) - lengthBrick;
		else if(i >= 25 && i <= 29)	tempR = outerR - (int)(lengthBrick/1.5) - lengthBrick;
		else if(i >= 30 && i <= 33)	tempR = outerR - (int)(lengthBrick/2) - lengthBrick;
		else if(i >= 34 && i <= 37)	tempR = outerR - (int)(lengthBrick/2.5) - lengthBrick;
//		else if(i >= 28 && i <= 30)	tempR = outerR - (int)(lengthBrick/3) - lengthBrick;				
		else if(i >= 38 && i <= 41)	tempR = outerR - (int)(lengthBrick/3.5) - lengthBrick;
		else if(i >= 42 && i <= 57)	tempR = outerR - (int)(lengthBrick/4) - lengthBrick;
		else if(i >= 58 && i <= 63) tempR = outerR - (int)(lengthBrick/3) - lengthBrick;
		else if(i >= 64 && i <= 65) tempR = outerR - (int)(lengthBrick/2.5) - lengthBrick;
		else if(i >= 66 && i <= 71) tempR = outerR - (int)(lengthBrick/2) - lengthBrick;
		else tempR = outerR - (int)(lengthBrick/1.5) - lengthBrick;
		
		outerIncreLineXX1 = tempR * Math.cos((2 * Math.PI) * i * degreeBrick / 360);
		int Xdecre1 = (int)(lengthBrick * Math.cos((2 * Math.PI) * i * degreeBrick / 360));
		
		outerIncreLineYY1 = tempR	* Math.sin((2 * Math.PI) * i * degreeBrick / 360);
		int Ydecre1 = (int)(lengthBrick * Math.sin((2 * Math.PI) * i * degreeBrick / 360));				
		
		XArray1[i] = (int) (centerX + outerIncreLineXX1);
		YArray1[i] = (int) (centerY - outerIncreLineYY1);
		
		for(int j = 0; j <= 3; ++j) {
			XDecre1[i][j] = XArray1[i] - j * Xdecre1;
			YDecre1[i][j] = YArray1[i] + j * Ydecre1;
		}
	}
		
		for (int i = 0; i <= numLine*2+1; i++) {
//			if(i != 36) continue;
//		for (int i = 0; i <= 0; i++) {				
			int tempR;
			if(i >= 0 && i <= 5)	tempR = outerR - (int)(lengthBrick/1.5) - 5 * lengthBrick;
			else if(i >= 6 && i <= 9)	tempR = outerR - (int)(lengthBrick/1.3) - 5 *  lengthBrick;				
			else if(i >= 10 && i <= 13)	tempR = outerR - (int)(lengthBrick/1.3) - 5 *  lengthBrick;
			else if(i >= 14 && i <= 16)	tempR = outerR - (int)(lengthBrick/1.2) - 5 *  lengthBrick;
			else if(i >= 17 && i <= 22)	tempR = outerR - (int)(lengthBrick/1.3) - 5 *  lengthBrick;
			else if(i >= 25 && i <= 29)	tempR = outerR - (int)(lengthBrick/1.5) - 5 *  lengthBrick;
			else if(i >= 30 && i <= 33)	tempR = outerR - (int)(lengthBrick/2) - 5 *  lengthBrick;
			else if(i >= 34 && i <= 37)	tempR = outerR - (int)(lengthBrick/2.5) - 5 *  lengthBrick;
//			else if(i >= 28 && i <= 30)	tempR = outerR - (int)(lengthBrick/3) - lengthBrick;				
			else if(i >= 38 && i <= 41)	tempR = outerR - (int)(lengthBrick/3.5) - 5 *  lengthBrick;
			else if(i >= 42 && i <= 57)	tempR = outerR - (int)(lengthBrick/4) - 5 *  lengthBrick;
			else if(i >= 58 && i <= 63) tempR = outerR - (int)(lengthBrick/3) - 5 *  lengthBrick;
			else if(i >= 64 && i <= 65) tempR = outerR - (int)(lengthBrick/2.5) - 5 *  lengthBrick;
			else if(i >= 66 && i <= 71) tempR = outerR - (int)(lengthBrick/2) - 5 *  lengthBrick;
			else tempR = outerR - (int)(lengthBrick/1.5) -  5 * lengthBrick;
			
			outerIncreLineXX2 = tempR * Math.cos((2 * Math.PI) * i * degreeBrick / 360);
			int Xdecre2 = (int)(lengthBrick * Math.cos((2 * Math.PI) * i * degreeBrick / 360));
			
			outerIncreLineYY2 = tempR	* Math.sin((2 * Math.PI) * i * degreeBrick / 360);
			int Ydecre2 = (int)(lengthBrick * Math.sin((2 * Math.PI) * i * degreeBrick / 360));				
			
			XArray2[i] = (int) (centerX + outerIncreLineXX2);
			YArray2[i] = (int) (centerY - outerIncreLineYY2);

			for(int j = 0; j <= 1; ++j) {
				XDecre2[i][j] = XArray2[i] - j * Xdecre2;
				YDecre2[i][j] = YArray2[i] + j * Ydecre2;
			}
		}		
		for (int i = 0; i <= numLine*2+1; i++) {
//		if(i != 36) continue;
//	for (int i = 0; i <= 0; i++) {				
		int tempR;
		if(i >= 0 && i <= 5)	tempR = outerR - (int)(lengthBrick/1.5) - 7 * lengthBrick;
		else if(i >= 6 && i <= 9)	tempR = outerR - (int)(lengthBrick/1.3) - 7 *  lengthBrick;				
		else if(i >= 10 && i <= 13)	tempR = outerR - (int)(lengthBrick/1.3) - 7 *  lengthBrick;
		else if(i >= 14 && i <= 16)	tempR = outerR - (int)(lengthBrick/1.2) - 7 *  lengthBrick;
		else if(i >= 17 && i <= 22)	tempR = outerR - (int)(lengthBrick/1.3) - 7 *  lengthBrick;
		else if(i >= 25 && i <= 29)	tempR = outerR - (int)(lengthBrick/1.5) - 7 *  lengthBrick;
		else if(i >= 30 && i <= 33)	tempR = outerR - (int)(lengthBrick/2) - 7 *  lengthBrick;
		else if(i >= 34 && i <= 37)	tempR = outerR - (int)(lengthBrick/2.5) - 7 *  lengthBrick;
//		else if(i >= 28 && i <= 30)	tempR = outerR - (int)(lengthBrick/3) - lengthBrick;				
		else if(i >= 38 && i <= 41)	tempR = outerR - (int)(lengthBrick/3.5) - 7 *  lengthBrick;
		else if(i >= 42 && i <= 57)	tempR = outerR - (int)(lengthBrick/4) - 7 *  lengthBrick;
		else if(i >= 58 && i <= 63) tempR = outerR - (int)(lengthBrick/3) - 7 *  lengthBrick;
		else if(i >= 64 && i <= 65) tempR = outerR - (int)(lengthBrick/2.5) - 7 *  lengthBrick;
		else if(i >= 66 && i <= 71) tempR = outerR - (int)(lengthBrick/2) - 7 *  lengthBrick;
		else tempR = outerR - (int)(lengthBrick/1.5) -  7 * lengthBrick;
		
		outerIncreLineXX3 = tempR * Math.cos((2 * Math.PI) * i * degreeBrick / 360);
		int Xdecre3 = (int)(lengthBrick * Math.cos((2 * Math.PI) * i * degreeBrick / 360));
		
		outerIncreLineYY3 = tempR	* Math.sin((2 * Math.PI) * i * degreeBrick / 360);
		int Ydecre3 = (int)(lengthBrick * Math.sin((2 * Math.PI) * i * degreeBrick / 360));				
		
		XArray3[i] = (int) (centerX + outerIncreLineXX3);
		YArray3[i] = (int) (centerY - outerIncreLineYY3);
		for(int j = 0; j <= 2; ++j) {
			XDecre3[i][j] = XArray3[i] - j * Xdecre3;
			YDecre3[i][j] = YArray3[i] + j * Ydecre3;
		}
	}			

		
		double outerIncreLineX, outerIncreLineY, innerIncreLineX, innerIncreLineY;
		int innerRTemp = innerR;
		innerR = outerR - numTrack * lengthBrick;
		for (int i = 0; i <= numLine; i++) { // 繪製直線
			outerIncreLineX = outerR
					* Math.sin((2 * Math.PI) * i * degreeBrick / 360);
			outerIncreLineXArray[i] = (int) (centerX + outerIncreLineX);
			outerIncreLineXArray1[i] = (int) (centerX - outerIncreLineX);
			outerIncreLineY = outerR - outerR
					* Math.cos((2 * Math.PI) * i * degreeBrick / 360);
			outerIncreLineYArray[i] = (int) (outerY + outerIncreLineY);
			outerIncreLineYArray1[i] = (int) (outerY + 2 * outerR - outerIncreLineY);
			innerIncreLineX = innerR
					* Math.sin((2 * Math.PI) * i * degreeBrick / 360);
			innerIncreLineXArray[i] = (int) (centerX + innerIncreLineX);
			innerIncreLineXArray1[i] = (int) (centerX - innerIncreLineX);
			innerIncreLineY = innerR - innerR
					* Math.cos((2 * Math.PI) * i * degreeBrick / 360);
			innerIncreLineYArray[i] = (int) (outerY + numTrack * lengthBrick + innerIncreLineY);
			innerIncreLineYArray1[i] = (int) (outerY + 2 * outerR - numTrack
					* lengthBrick - innerIncreLineY);
		}
		innerR = innerRTemp;

		int outerR1 = outerR - numTrack * lengthBrick, outerY1 = outerY + numTrack
				* lengthBrick;
		double siteouterIncreLineX, siteouterIncreLineY, siteinnerIncreLineX, siteinnerIncreLineY;
		double site2outerIncreLineX, site2outerIncreLineY, site2innerIncreLineX, site2innerIncreLineY;
		// innerR = outerR - numTrack1 * lengthBrick
		for (int i = 7, j = 0; i <= 11; ++i, ++j) { // 繪製直線
			siteouterIncreLineX = outerR1
					* Math.sin((2 * Math.PI) * i * degreeBrick / 360);
			siteouterIncreLineXArray[j] = (int) (centerX + siteouterIncreLineX);
			siteouterIncreLineXArray1[j] = (int) (centerX - siteouterIncreLineX);
			site2outerIncreLineX = outerR1
					* Math.sin((2 * Math.PI)
							* (i + (endAngelRPlayer - startAngelRPlayer - 4)) * degreeBrick
							/ 360);
			site2outerIncreLineXArray[j] = (int) (centerX + site2outerIncreLineX);
			site2outerIncreLineXArray1[j] = (int) (centerX - site2outerIncreLineX);
			siteouterIncreLineY = outerR1 - outerR1
					* Math.cos((2 * Math.PI) * i * degreeBrick / 360);
			siteouterIncreLineYArray[j] = (int) (outerY1 + siteouterIncreLineY);
			siteouterIncreLineYArray1[j] = (int) (outerY1 + 2 * outerR1 - siteouterIncreLineY);
			site2outerIncreLineY = outerR1
					- outerR1
					* Math.cos((2 * Math.PI)
							* (i + (endAngelRPlayer - startAngelRPlayer - 4)) * degreeBrick
							/ 360);
			site2outerIncreLineYArray[j] = (int) (outerY1 + site2outerIncreLineY);
			site2outerIncreLineYArray1[j] = (int) (outerY1 + 2 * outerR1 - site2outerIncreLineY);
			siteinnerIncreLineX = innerR
					* Math.sin((2 * Math.PI) * i * degreeBrick / 360);
			siteinnerIncreLineXArray[j] = (int) (centerX + siteinnerIncreLineX);
			siteinnerIncreLineXArray1[j] = (int) (centerX - siteinnerIncreLineX);
			site2innerIncreLineX = innerR
					* Math.sin((2 * Math.PI)
							* (i + (endAngelRPlayer - startAngelRPlayer - 4)) * degreeBrick
							/ 360);
			site2innerIncreLineXArray[j] = (int) (centerX + site2innerIncreLineX);
			site2innerIncreLineXArray1[j] = (int) (centerX - site2innerIncreLineX);
			siteinnerIncreLineY = innerR - innerR
					* Math.cos((2 * Math.PI) * i * degreeBrick / 360);
			siteinnerIncreLineYArray[j] = (int) (outerY + numTrack1 * lengthBrick + siteinnerIncreLineY);
			siteinnerIncreLineYArray1[j] = (int) (outerY1 + 2 * outerR1
					- (numTrack1 - numTrack) * lengthBrick - siteinnerIncreLineY);
			site2innerIncreLineY = innerR
					- innerR
					* Math.cos((2 * Math.PI)
							* (i + (endAngelRPlayer - startAngelRPlayer - 4)) * degreeBrick
							/ 360);
			site2innerIncreLineYArray[j] = (int) (outerY + numTrack1 * lengthBrick + site2innerIncreLineY);
			site2innerIncreLineYArray1[j] = (int) (outerY1 + 2 * outerR1
					- (numTrack1 - numTrack) * lengthBrick - site2innerIncreLineY);
		}

		for (int i = 0; i <= numTrack1; i++) { // 繪製同心圓
			ovalCordX[i] = outerX + lengthBrick * i;
			ovalCordY[i] = outerY + lengthBrick * i;
			ovalCordL[i] = 2 * outerR - 2 * lengthBrick * i;
			ovalCordW[i] = 2 * outerR - 2 * lengthBrick * i;
		}
	}

		PlayMusic musicThread;
		class PlayMusic extends Thread{
		   private AudioClip sound1;
				public void run() {
		     URL url = this.getClass().getResource("YoungBetray.Wav");
		     sound1 = Applet.newAudioClip(url);
		     sound1.loop();
				}
			}
		
	public Hint3_P1_Speed_Unnumbered() {
		setExtendedState(MAXIMIZED_BOTH);
		setLayout(new BorderLayout());
		tetris = new Tetris();
		tetris.setBackground(colorJPanel); // JPanel背景色。
		add(tetris, BorderLayout.CENTER);
		txtField = new JTextArea();
		txtField.setFont(new Font("SanSerif", Font.PLAIN, 30));
		add(txtField, BorderLayout.EAST);
		musicThread = new PlayMusic();
//		musicThread.setPriority(7);
//		musicThread.start();
	}

	Tetris tetris; // 為了在main中,讓JPanel能接收鍵盤事件而設的!
	JTextArea txtField;
	public static void main(String[] args) {
		Hint3_P1_Speed_Unnumbered frame = new Hint3_P1_Speed_Unnumbered();
//		testframe = frame;   //****************************
		int i = 1234;
		frame.setTitle("Hint3_P1_Speed_Unnumbered");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.widthFrame = frame.getSize().width;
		frame.heightFrame = frame.getSize().height;
		frame.setSchelton();
		frame.goToPlay();
	}

	void gameOver() {
		Graphics2D g2d = (Graphics2D) this.tetris.getGraphics();
		Font fo = new Font("Serif", Font.PLAIN, 500);
		this.tetris.setFont(fo);
		for (int i = 0; i <= 100000; i++)
			;
		for (int i = 0; i <= 1000; i = i + 100)
			for (int j = 0; j <= 700; j = j + 20)
				g2d.drawString("GAME OVER", i, j);
	}

	int rotateChangeInterval = 0;

	void setNewBrick() {
		// 設定旋轉方向
		++rotateChangeInterval;
		if (rotateChangeInterval >= 3) { // 是否到達改變旋轉方向週期.
			rotateChangeInterval = 0;
			rotateState = rotateClockwise; // 紀錄之前旋轉方向.
			int temp1 = (int) (100 * Math.random());
			if (temp1 <= 50)
				rotateClockwise = true;
			else
				rotateClockwise = false;
			if (rotateState != rotateClockwise) // 紀錄旋轉方向是否有改變.
				rotateSpeedZero = true;
		}
		// 遞移減速方塊布林變數.
		isSpeedDownBrick = nextIsSpeedDownBrick;
		nextIsSpeedDownBrick = next2IsSpeedDownBrick;
		next2IsSpeedDownBrick = next3IsSpeedDownBrick;
		// 亂數取得方塊布林變數.
		int temp = (int) (100 * Math.random());
		if (temp <= 30)
			next3IsSpeedDownBrick = true;
		else
			next3IsSpeedDownBrick = false;

		// 遞移方塊型態.
		currentBrickType = nextCurrentBrickType;
		nextCurrentBrickType = next2CurrentBrickType;
		next2CurrentBrickType = next3CurrentBrickType;
		// 亂數取得方塊型態.
		next3CurrentBrickType = (int) (bricksType.length * Math.random());

		currentBrickSubType = 0;

		currentBrickRotateType = 0;

		increAngel = 0;
		increRadius = 0;
		for (int i = 0; i <= bricksType[currentBrickType][currentBrickSubType].length - 1; ++i) {
			int increAngel = bricksType[currentBrickType][currentBrickSubType][i][0];
			int increRadius = bricksType[currentBrickType][currentBrickSubType][i][1];
			if (RBrickCanNotChange)
				mapGame[currentBrickAngel + increAngel][currentBrickRadius
						+ increRadius] = -100;
			else if (isSpeedDownBrick)
				mapGame[currentBrickAngel + increAngel][currentBrickRadius
						+ increRadius] = -90;
			else
				mapGame[currentBrickAngel + increAngel][currentBrickRadius
						+ increRadius] = -10;
		}
		setBrickNotInput = false;
	}

	void setNewBrickL() {
		// 遞移減速方塊布林變數.
		isSpeedDownBrickL = nextIsSpeedDownBrickL;
		nextIsSpeedDownBrickL = next2IsSpeedDownBrickL;
		next2IsSpeedDownBrickL = next3IsSpeedDownBrickL;
		// 亂數取得方塊布林變數.
		int temp = (int) (100 * Math.random());
		if (temp <= 30)
			next3IsSpeedDownBrickL = true;
		else
			next3IsSpeedDownBrickL = false;

		// 遞移方塊型態.
		currentBrickTypeL = nextCurrentBrickTypeL;
		nextCurrentBrickTypeL = next2CurrentBrickTypeL;
		next2CurrentBrickTypeL = next3CurrentBrickTypeL;
		// 亂數取得方塊型態.
		next3CurrentBrickTypeL = (int) (bricksType.length * Math.random());

		currentBrickSubTypeL = 0;

		currentBrickRotateTypeL = 0;

		// currentBrickTypeL = (int) (bricksType.length * Math.random());
		currentBrickSubTypeL = 0;
		currentBrickRotateTypeL = 0;

		increAngelL = 0;
		increRadiusL = 0;
		for (int i = 0; i <= bricksType[currentBrickTypeL][currentBrickSubTypeL].length - 1; ++i) {
			int increAngelL = bricksType[currentBrickTypeL][currentBrickSubTypeL][i][0];
			int increRadiusL = bricksType[currentBrickTypeL][currentBrickSubTypeL][i][1];
			if (LBrickCanNotChange)
				mapGameL[currentBrickAngelL + increAngelL][currentBrickRadiusL
						+ increRadiusL] = -100;
			else if (isSpeedDownBrickL)
				mapGameL[currentBrickAngelL + increAngelL][currentBrickRadiusL
						+ increRadiusL] = -90;
			else
				mapGameL[currentBrickAngelL + increAngelL][currentBrickRadiusL
						+ increRadiusL] = -10;
		}
		setBrickNotInputL = false;
	}

	boolean newBrickOK() {
		for (int i = startAngelRPlayer + newBrickIncre; i <= startAngelRPlayer
				+ newBrickIncre + 2; i++) {
			if (mapGame[i][currentBrickRadius] == -10) {
				return false; // 不可以起始一個新的方塊
			}
		}
		return true; // 可以起始一個新的方塊
	}

	boolean newBrickOKL() {
		for (int i = startAngelLPlayer + newBrickIncreL; i <= startAngelLPlayer
				+ newBrickIncreL + 2; i++) {
			if (mapGameL[i][currentBrickRadiusL] == -10) {
				return false; // 不可以起始一個新的方塊
			}
		}
		return true; // 可以起始一個新的方塊
	}

	void goToPlay() {
		this.tetris.requestFocus();// 讓JPanel接受鍵盤輸入

		int temp = (int) (100 * Math.random());
		if (temp <= 30)
			isSpeedDownBrick = true;
		else
			isSpeedDownBrick = false;
		temp = (int) (100 * Math.random());
		if (temp <= 30)
			isSpeedDownBrickL = true;
		else
			isSpeedDownBrickL = false;

		temp = (int) (100 * Math.random());
		if (temp <= 30)
			nextIsSpeedDownBrick = true;
		else
			nextIsSpeedDownBrick = false;
		temp = (int) (100 * Math.random());
		if (temp <= 30)
			nextIsSpeedDownBrickL = true;
		else
			nextIsSpeedDownBrickL = false;

		temp = (int) (100 * Math.random());
		if (temp <= 30)
			next2IsSpeedDownBrick = true;
		else
			next2IsSpeedDownBrick = false;
		temp = (int) (100 * Math.random());
		if (temp <= 30)
			next2IsSpeedDownBrickL = true;
		else
			next2IsSpeedDownBrickL = false;

		temp = (int) (100 * Math.random());
		if (temp <= 30)
			next3IsSpeedDownBrick = true;
		else
			next3IsSpeedDownBrick = false;
		temp = (int) (100 * Math.random());
		if (temp <= 30)
			next3IsSpeedDownBrickL = true;
		else
			next3IsSpeedDownBrickL = false;

		gameStart = true;
		fallingOver = false;
		fallingOverL = false;// ****
		rotateIndex = 0;// 伴隨rotateAngel
		rotateIndexL = 0;// 伴隨rotateAngel
		rotateAngel = 0;// ****
		rotateAngelL = 0;// ****
		numDeleted = 0;
		numDeletedL = 0;
		// while (gameStart) { // 起始一個方塊, 開始落下.
		++brickFallingNumber;
		++rotateAngel;
		++rotateAngelL;// ****
		// --rotateIndex;
		// --rotateIndexL;//****
		if (rotateIndex < 0)
			rotateIndex = rotateIndex + 360 / degreeBrick;
		if (rotateIndexL < 0)
			rotateIndexL = rotateIndexL + 360 / degreeBrick;// ****
		fallingOver = false;
		fallingOverL = false;// ****
		brickToSite = false;
		brickToSiteL = false;// ****
		currentBrickAngel = startAngelRPlayer + (2 - 1) + newBrickIncre;// 有增加newBrickIncre歐
		currentBrickAngelL = startAngelLPlayer + (2 - 1) + newBrickIncreL;// 有增加newBrickIncre歐
		currentBrickRadius = (int) ((cordRadiusMax + 1) * Math.random());
		currentBrickRadiusL = (int) ((cordRadiusMax + 1) * Math.random());// ****
		currentBrickRadius = cordRadiusMax / 2 + 1;
		currentBrickRadiusL = cordRadiusMax / 2;// ****
		if (!newBrickOK()) { // 檢查是否可以起始一個方塊, 開始落下.
			gameOver();
		}
		if (!newBrickOKL()) { // 檢查是否可以起始一個方塊, 開始落下.//****
		} // ****
		// setNewBrick(); // 在mapGame[][]與mapColor之中, 進行新方塊的初使工作.
		// setNewBrickL(); // ****
		System.out.println("Before OneLoop");
		gameOneLoop();

		try {
			Thread.sleep(newBrickTime);
		} catch (InterruptedException e) {
			// TODO 自動產生 catch 區塊
			e.printStackTrace();
		}
		// } // End of while
	} // End of goToPlay()

	boolean moreToErase;

	void markSameRow() {
		for (int i = endAngelRPlayer - 1; i >= startAngelRPlayer; --i) { // 從最底部開始往上檢查(針對各各逕向列)
			boolean findRow = true;
			for (int j = 0; j <= cordRadiusMax; ++j) {// 尋找可以消除的一列.
				if (!(mapGame[i][j] == -10 || mapGame[i][j] == -40
						|| mapGame[i][j] == -90 || mapGame[i][j] == -100)) { // 沒有要消除的逕向列.
					findRow = false;
					// 將-90回復為-10
					for (int k = 0; k <= cordRadiusMax; ++k)
						if (mapGame[i][k] == -90 || mapGame[i][k] == -100)
							mapGame[i][k] = -10;
					break;
				}
			} // 可以執行完此FOR迴圈, 表示找到要消除的一列.
			// 將-90回復為-10
			for (int k = 0; k <= cordRadiusMax; ++k)
				if (mapGame[i][k] == -90 || mapGame[i][k] == -100)
					mapGame[i][k] = -10;
			if (findRow == false)
				continue;
			// 執行消除的工作.
			++numDeleted;
			moreToErase = true;
			for (int cordAngel = i; cordAngel >= startAngelRPlayer; --cordAngel) {
				for (int cordRadius = 0; cordRadius <= cordRadiusMax; ++cordRadius) {
					if (mapGame[cordAngel - 1][cordRadius] != -50
							&& mapGame[cordAngel - 1][cordRadius] != -40
							&& (cordAngel - 1) >= 8) {
						mapGame[cordAngel][cordRadius] = mapGame[cordAngel - 1][cordRadius];
					}
				}
			}
			return;
		} // end of for
		moreToErase = false;
	}

	boolean moreToEraseL;

	void markSameRowL() {
		for (int i = endAngelLPlayer - 1; i >= startAngelLPlayer; --i) { // 從最底部開始往上檢查(針對各各逕向列)
			boolean findRow = true;
			for (int j = 0; j <= cordRadiusMax; ++j) {// 尋找可以消除的一列.
				if (!(mapGameL[i][j] == -10 || mapGameL[i][j] == -40
						|| mapGameL[i][j] == -90 || mapGameL[i][j] == -100)) { // 沒有要消除的逕向列.
					findRow = false;
					// 將-90回復為-10
					for (int k = 0; k <= cordRadiusMax; ++k)
						if (mapGameL[i][k] == -90 || mapGameL[i][k] == -100)
							mapGameL[i][k] = -10;
					break;
				}
			} // 可以執行完此FOR迴圈, 表示找到要消除的一列.
			for (int k = 0; k <= cordRadiusMax; ++k)
				if (mapGameL[i][k] == -90 || mapGameL[i][k] == -100)
					mapGameL[i][k] = -10;
			if (findRow == false)
				continue;
			// 執行消除的工作.
			++numDeletedL;
			moreToEraseL = true;
			for (int cordAngelL = i; cordAngelL >= startAngelLPlayer; --cordAngelL) {
				for (int cordRadiusL = 0; cordRadiusL <= cordRadiusMax; ++cordRadiusL) {
					if (mapGameL[cordAngelL - 1][cordRadiusL] != -50
							&& mapGameL[cordAngelL - 1][cordRadiusL] != -40
							&& (cordAngelL - 1) >= 8) {
						mapGameL[cordAngelL][cordRadiusL] = mapGameL[cordAngelL - 1][cordRadiusL];
					}
				}
			}
			return;
		} // end of for
		moreToErase = false;
	}

	void markErasedBrick() {
		moreToErase = false;
		markSameRow();
	}

	void markErasedBrickL() {
		moreToEraseL = false;
		markSameRowL();
	}

	void scanAndErase() {
		numDeleted = 0;
		moreToErase = true;
		while (moreToErase) { // 若掃描發現有方塊要消除, 則必須再進行一次掃描, 因為方塊的消除,
			markErasedBrick(); // 有可能引發更多方塊的消除.
			try {
				Thread.sleep(testTime);
			} catch (InterruptedException e) {
				// TODO 自動產生 catch 區塊
				e.printStackTrace();
			}
		}
		tetris.repaint();
	}

	void scanAndEraseL() {
		numDeletedL = 0;
		moreToEraseL = true;
		while (moreToEraseL) { // 若掃描發現有方塊要消除, 則必須再進行一次掃描, 因為方塊的消除,
			markErasedBrickL(); // 有可能引發更多方塊的消除.
			try {
				Thread.sleep(testTime);
			} catch (InterruptedException e) {
				// TODO 自動產生 catch 區塊
				e.printStackTrace();
			}
		}
		tetris.repaint();
	}

	boolean fallingOK() {
		if (overR)
			return false;
		for (int i = 0; i <= bricksFallingType[currentBrickType][currentBrickSubType].length - 1; ++i) {
			if (i == 0)// 第一組座標(i為0)是下落檢查方塊組中最底部的方塊, 其1st整數表示Angel座標.
				// 此if用以檢查是否到達mapGame底部
				if ((bricksFallingType[currentBrickType][currentBrickSubType][i][0] + currentBrickAngel) > endAngelRPlayer - 1) {
					fallingOver = true;
					return false;
				}
			int increAngel = bricksFallingType[currentBrickType][currentBrickSubType][i][0];
			int increRadius = bricksFallingType[currentBrickType][currentBrickSubType][i][1];
			if (mapGame[currentBrickAngel + increAngel][currentBrickRadius
					+ increRadius] == -10
					|| mapGame[currentBrickAngel + increAngel][currentBrickRadius
							+ increRadius] == -40) {
				fallingOver = true;
				return false;
			}
		}
		return true;
	}

	boolean fallingOKL() {
		if (overL)
			return false;
		for (int i = 0; i <= bricksFallingType[currentBrickTypeL][currentBrickSubTypeL].length - 1; ++i) {
			if (i == 0)// 第一組座標(i為0)是下落檢查方塊組中最底部的方塊, 其1st整數表示Angel座標.
				// 此if用以檢查是否到達mapGame底部
				if ((bricksFallingType[currentBrickTypeL][currentBrickSubTypeL][i][0] + currentBrickAngelL) > endAngelLPlayer - 1) {
					fallingOverL = true;
					return false;
				}
			int increAngelL = bricksFallingType[currentBrickTypeL][currentBrickSubTypeL][i][0];
			int increRadiusL = bricksFallingType[currentBrickTypeL][currentBrickSubTypeL][i][1];
			if (mapGameL[currentBrickAngelL + increAngelL][currentBrickRadiusL
					+ increRadiusL] == -10
					|| mapGameL[currentBrickAngelL + increAngelL][currentBrickRadiusL
							+ increRadiusL] == -40) {
				fallingOverL = true;
				return false;
			}
		}
		return true;
	}

	void setRPlayerFalling() {
		if (overR)
			return;
		for (int i = 0; i <= bricksType[currentBrickType][currentBrickSubType].length - 1; ++i) {
			int increAngel = bricksType[currentBrickType][currentBrickSubType][i][0];
			int increRadius = bricksType[currentBrickType][currentBrickSubType][i][1];
			mapGame[currentBrickAngel + increAngel][currentBrickRadius + increRadius] = -20;
		}
		currentBrickAngel = 1 + currentBrickAngel; // 設定方塊下落一格.
		for (int i = 0; i <= bricksType[currentBrickType][currentBrickSubType].length - 1; ++i) {
			int increAngel = bricksType[currentBrickType][currentBrickSubType][i][0];
			int increRadius = bricksType[currentBrickType][currentBrickSubType][i][1];
			if (RBrickCanNotChange)
				mapGame[currentBrickAngel + increAngel][currentBrickRadius
						+ increRadius] = -100;
			else if (isSpeedDownBrick)
				mapGame[currentBrickAngel + increAngel][currentBrickRadius
						+ increRadius] = -90;
			else
				mapGame[currentBrickAngel + increAngel][currentBrickRadius
						+ increRadius] = -10;
			// mapGame[currentBrickAngel + increAngel][currentBrickRadius +
			// increRadius] = -10;
		}
		// 設定要消除的方塊.
		mapGame[currentBrickAngel - 2][currentBrickRadius] = -20; // 設定為-30,
		// 表示要消除的方塊.
	}

	void setLPlayerFalling() {
		if (overL)
			return;
		for (int i = 0; i <= bricksType[currentBrickTypeL][currentBrickSubTypeL].length - 1; ++i) {
			int increAngelL = bricksType[currentBrickTypeL][currentBrickSubTypeL][i][0];
			int increRadiusL = bricksType[currentBrickTypeL][currentBrickSubTypeL][i][1];
			mapGameL[currentBrickAngelL + increAngelL][currentBrickRadiusL
					+ increRadiusL] = -20;
		}
		currentBrickAngelL = 1 + currentBrickAngelL; // 設定方塊下落一格.
		for (int i = 0; i <= bricksType[currentBrickTypeL][currentBrickSubTypeL].length - 1; ++i) {
			int increAngelL = bricksType[currentBrickTypeL][currentBrickSubTypeL][i][0];
			int increRadiusL = bricksType[currentBrickTypeL][currentBrickSubTypeL][i][1];
			if (LBrickCanNotChange)
				mapGameL[currentBrickAngelL + increAngelL][currentBrickRadiusL
						+ increRadiusL] = -100;
			else if (isSpeedDownBrickL)
				mapGameL[currentBrickAngelL + increAngelL][currentBrickRadiusL
						+ increRadiusL] = -90;
			else
				mapGameL[currentBrickAngelL + increAngelL][currentBrickRadiusL
						+ increRadiusL] = -10;
		}
		// 設定要消除的方塊.
		mapGameL[currentBrickAngelL - 2][currentBrickRadiusL] = -20; // 設定為-30,
	}

	void centrifugal() {// 存在的徑向列, 一定是不可消除列.
		int firstRowCentrifugal = 0, numRowCentrifugal = 0;
		boolean findBrokenRow = false;
		for (int i = startAngelRPlayer; i <= endAngelRPlayer - 1; ++i) { // 從最頂部開始往下檢查
			for (int j = 0; j <= cordRadiusMax; ++j) {// 尋找第一列.
				if (mapGame[i][j] == -10) {
					firstRowCentrifugal = i;
					findBrokenRow = true;
					break;// 找到不可以消除的逕向列, 所以跳出內部for迴圈.
				}
			} // end of inner for
			if (findBrokenRow)
				break; // 找到不可以消除的逕向列, 所以跳出外部for迴圈.
		} // end of outter for

		if (!findBrokenRow)
			return; // 很罕見, 徑向列被完全消除.
		// 執行離心作業.
		for (int cordAngel = firstRowCentrifugal; // cordAngel表示目前徑向座標,
		// 就是要進行離心作業的徑向列.
		cordAngel <= endAngelRPlayer - 1; ++cordAngel) {
			if (numRowCentrifugal >= 3)
				break;// 已經離心3個徑向列, 所以跳出.
			// 以下針對單一列, 進行離心移動.
			int indexRadius = 0; // 作為移動計數器.
			boolean rowCentrifugal = false;
			for (int cordRadius = 0; cordRadius <= cordRadiusMax; ++cordRadius) {

				if (cordRadius <= cordRadiusMax - 1) {// 是否有真正進行離心作業.//注意是 != -10
					if (!rowCentrifugal && mapGame[cordAngel][cordRadius] != -10
							&& mapGame[cordAngel][cordRadius + 1] == -10) {
						++numRowCentrifugal;// 有真正 進行離心作業, 才遞增.
						rowCentrifugal = true;// rowCentrifugal使得numRowCentrifugal正確地遞增.
					}
				}

				if (mapGame[cordAngel][cordRadius] == -10) {
					mapGame[cordAngel][cordRadius] = -20;
					mapGame[cordAngel][indexRadius] = -10;
					++indexRadius;
				}

			}// end of inner for

			if (rowCentrifugal) {
				tetris.repaint();
				try {
					Thread.sleep(200);
				} catch (InterruptedException exc) {
				}
			}

		}// end of outter for
	}// end of centrifugal()

	void centrifugalL() {// 存在的徑向列, 一定是不可消除列.
		int firstRowCentrifugal = 0, numRowCentrifugal = 0;
		boolean findBrokenRow = false;
		for (int i = startAngelLPlayer; i <= endAngelLPlayer - 1; ++i) { // 從最頂部開始往下檢查
			for (int j = 0; j <= cordRadiusMax; ++j) {// 尋找第一列.
				if (mapGameL[i][j] == -10) {
					firstRowCentrifugal = i;
					findBrokenRow = true;
					break;// 找到不可以消除的逕向列, 所以跳出內部for迴圈.
				}
			} // end of inner for
			if (findBrokenRow)
				break; // 找到不可以消除的逕向列, 所以跳出外部for迴圈.
		} // end of outter for

		if (!findBrokenRow)
			return; // 很罕見, 徑向列被完全消除.
		// 執行離心作業.
		for (int cordAngel = firstRowCentrifugal; // cordAngel表示目前徑向座標,
		// 就是要進行離心作業的徑向列.
		cordAngel <= endAngelLPlayer - 1; ++cordAngel) {
			if (numRowCentrifugal >= 3)
				break;// 已經離心3個徑向列, 所以跳出.
			// 以下針對單一列, 進行離心移動.
			int indexRadius = 0; // 作為移動計數器.
			boolean rowCentrifugal = false;
			for (int cordRadius = 0; cordRadius <= cordRadiusMax; ++cordRadius) {

				if (cordRadius <= cordRadiusMax - 1) {// 是否有真正進行離心作業.//注意是 != -10
					if (!rowCentrifugal && mapGameL[cordAngel][cordRadius] != -10
							&& mapGameL[cordAngel][cordRadius + 1] == -10) {
						++numRowCentrifugal;// 有真正 進行離心作業, 才遞增.
						rowCentrifugal = true;// rowCentrifugal使得numRowCentrifugal正確地遞增.
					}
				}

				if (mapGameL[cordAngel][cordRadius] == -10) {
					mapGameL[cordAngel][cordRadius] = -20;
					mapGameL[cordAngel][indexRadius] = -10;
					++indexRadius;
				}

			}// end of inner for

			if (rowCentrifugal) {
				tetris.repaint();
				try {
					Thread.sleep(200);
				} catch (InterruptedException exc) {
				}
			}

		}// end of outter for
	}// end of centrifugal()

	void settxtField() {
		txtField.setText("\n" + "<==第3個方塊\n\n" + "<==第2個方塊\n\n" + "<==第1個方塊\n\n"
				+ "<==第3個方塊\n\n" + "<==第2個方塊\n\n" + "<==第1個方塊\n" + "速度: " + speed
				+ "       \n" + "煞車: " + brake + "       \n" + "rotateSpeed: "
				+ rotateSpeed + "       \n" + "列數: " + totalDeleted
				+ "       \n" + "圈數: " + circleRotate);
	}

	int brake = 0, speed = 1, speedIncreaseInterval = 0, speedIncreaseCount = 0,
			totalDeleted = 0, circleRotate = 0;

	boolean speedIncrease = true;

	boolean inGameLoop = false, inGameLoopL = false;
  int fallingConstant = 10, fallingInterval = fallingConstant;
  int speedContanst = 1, rotateSpeed = 55;
	void gameOneLoop() {
		boolean stopR = false, stopL = false;
		while (true) {
			tetris.repaint();
			try {
				Thread.sleep(rotateSpeed);
			} catch (InterruptedException exc) {
			}

			// if(nowRPlayer && !overR) {
			if (!fallingOK() || brickToSite)
				stopR = true; // 方塊不可以再下落了.
			if (inGameLoop) {
				setNewBrick();
				if(rotateSpeed <= 19) isSpeedDownBrick = true;
				inGameLoop = false;
			}
			if (!stopR) {
				--fallingInterval;
					if(fallingInterval <= 0) {
					setRPlayerFalling(); // 方塊的自由下落.
					fallingInterval = fallingConstant;
				}
				settxtField();
				// centrifugalValue = 0;
				speed = speedContanst;
				if (!rotateClockwise)
					rotateAngel = rotateAngel + speed;// 逆時針轉動
				else
					rotateAngel = rotateAngel - speed;// 順時針轉動
				if (speedIncreaseCount >= 6) {// 方塊每落下一格, 則遞增speedIncreaseInterval.
					// 依速度大小, 作為遞增多寡的依據.
					if (speed >= 3)
						speedIncreaseInterval = speedIncreaseInterval + 1;
					if (speed < 3)
						speedIncreaseInterval = speedIncreaseInterval + 3;
					speedIncreaseCount = 0;
				} else
					++speedIncreaseCount;

			}// end of if (!stopR)
			else {
				if (!overR) {
					scanAndErase(); // 其中有repaint().//右玩家掃描完畢.
					rotateSpeed = rotateSpeed -1;
					if (numDeleted != 0) {
						rotateSpeed = rotateSpeed +  3*numDeleted; // 速度依消去的列數減少.
						if (isSpeedDownBrick) // 如果是特殊方塊再減一次.
							rotateSpeed = rotateSpeed + 3*numDeleted;
						if (speed < 0)
							speed = 0;
					}
					
					setBrickNotInput = true;
					if (RBrickCanNotChange)
						RBrickCanNotChange = false;// 左玩家已經用掉交換方塊, 故左玩家回到可交換狀態.
					if (LPlayerChangRPlayer)
						LPlayerChangRPlayer = false;// 
					// 以下進行離心作業.
					// testCentrifugal = true;
					if (speed >= centrifugalValue && testCentrifugal && numDeleted > 0) { // 開始離心力
						centrifugal(); // testCentrifugal只是測試布林值, 表示開始離心作業.
						testCentrifugal = false;
					}

					totalDeleted = totalDeleted + numDeleted;
					if (speedIncreaseInterval >= 5) { // 速度增加.
						if (speed <= 12)
							speed = speed + 2;
						if (speed >= 12)
							speed = 12;
						speedIncreaseInterval = 0;
					}
					// if(!overL) nowRPlayer = false; //因為要輪流, 所以右玩家SCAN完, 設為TRUE, 換左玩家.
					fallingOver = false;
					brickToSite = false;
					currentBrickAngel = startAngelRPlayer + (2 - 1) + newBrickIncre;// 有增加newBrickIncre歐
					currentBrickRadius = (int) ((cordRadiusMax + 1) * Math.random());
					currentBrickRadius = cordRadiusMax / 2 + 1;
					if (!newBrickOK()) {// 只會傳回true或false
						overR = true;// 一方玩家結束, 將overR設為true.
					}// gameOver();
					else {// 可以起始一個新的方塊.
					// setNewBrick();
						inGameLoop = true;
						stopR = false;
						numDeleted = 0;
						// ++rotateAngel;
						try {
							Thread.sleep(newBrickTime);
						} catch (InterruptedException e) {
							// TODO 自動產生 catch 區塊
							e.printStackTrace();
						}
					}
				}
			} // end of else
			// }//end of nowRPlayer

			// else if(!nowRPlayer && !overL){//start of nowLPlayer
			// if (!fallingOKL() || brickToSiteL)
			// stopL = true; // 方塊不可以再下落了.
			// if(inGameLoopL) {
			// setNewBrickL();
			// inGameLoopL = false;
			// }
			// if (!stopL) {
			// setLPlayerFalling(); // 方塊的自由下落.
			// settxtField();
			// // centrifugalValue = 0;
			// speed = 0;
			// if (!rotateClockwise)
			// rotateAngel = rotateAngel + speed;// 逆時針轉動
			// else
			// rotateAngel = rotateAngel - speed;// 順時針轉動
			// if (speedIncreaseCount == 6) {// 方塊每落下一格, 則遞增speedIncreaseInterval.
			// // 依速度大小, 作為遞增多寡的依據.
			// if (speed >= 3)
			// speedIncreaseInterval = speedIncreaseInterval + 1;
			// if (speed < 3)
			// speedIncreaseInterval = speedIncreaseInterval + 3;
			// speedIncreaseCount = 0;
			// } else
			// ++speedIncreaseCount;
			//				
			// }//end of if (!stopR)
			// else {
			// if (!overL) {
			// scanAndEraseL(); // 其中有repaint().
			// setBrickNotInputL = true;
			// if(LBrickCanNotChange) LBrickCanNotChange = false;//左玩家已經用掉交換方塊,
			// 故左玩家回到可交換狀態.
			// if(RPlayerChangLPlayer) RPlayerChangLPlayer = false;
			// // 以下進行離心作業.
			// testCentrifugalL = true;
			// if (speed >= centrifugalValue && testCentrifugalL && numDeletedL > 0) {
			// // 開始離心力
			// centrifugalL(); // testCentrifugal只是測試布林值, 表示開始離心作業.
			// testCentrifugalL = false;
			// }
			//
			// totalDeleted = totalDeleted + numDeletedL;
			// if (speedIncreaseInterval >= 10) { // 速度增加.
			// if (speed <= 6)
			// speed = speed + 2;
			// if (speed >= 6)
			// speed = 6;
			// speedIncreaseInterval = 0;
			// }
			// if (numDeletedL != 0) {
			// speed = speed - numDeletedL; // 速度依消去的列數減少.
			// if (isSpeedDownBrickL) // 如果是特殊方塊再減一次.
			// speed = speed - numDeletedL;
			// if (speed < 0)
			// speed = 0;
			// }
			// if(!overR) nowRPlayer = true; //因為要輪流, 所以左玩家SCAN完, 設為TRUE, 換右玩家.
			// fallingOverL = false;
			// brickToSiteL = false;
			// currentBrickAngelL = startAngelLPlayer + (2 - 1) + newBrickIncreL;//
			// 有增加newBrickIncre歐
			// currentBrickRadiusL = (int) ((cordRadiusMax + 1) * Math.random());
			// currentBrickRadiusL = cordRadiusMax / 2;
			// if (!newBrickOKL()) {//只會傳回true或false
			// overL = true; //一方玩家結束, 將overL設為true.
			// }// gameOver();
			// else {//可以起始一個新的方塊.
			// // setNewBrickL();
			// inGameLoopL = true;
			// stopL = false;
			// numDeletedL = 0;
			// // ++rotateAngel;
			// try {
			// Thread.sleep(newBrickTime);
			// } catch (InterruptedException e) {
			// // TODO 自動產生 catch 區塊
			// e.printStackTrace();
			// }
			// }
			// }
			// } //end of else
			// }//end of nowLPlayer
		}// End Of While(GameLoop)
	}// End Of GameOneLoop

	class Tetris extends JPanel { // 可以設定背景、前景顏色
		public void clearBrick(Graphics2D g2d, int cordAngel, int cordRadius) {
			g2d.setColor(colorJPanel);
			if (!rotateClockwise)
				cordAngel = cordAngel - rotateAngel;
			else
				cordAngel = cordAngel + rotateAngel;
			g2d.fillArc(outerX + cordRadius * lengthBrick, outerY + cordRadius
					* lengthBrick,// 正方形左上角座標
					2 * outerR - 2 * cordRadius * lengthBrick, 2 * outerR - 2
							* cordRadius * lengthBrick,// 正方形的寬與高
					90 - cordAngel * degreeBrick, -degreeBrick);// 扇形的開始角度/ 終止角度/因為負角度是順時鐘
		}// end of paintBrick

		public void clearBrickL(Graphics2D g2d, int cordAngel, int cordRadius) {
			g2d.setColor(colorJPanel);
			cordAngel = (360 / degreeBrick) - 1 - cordAngel - rotateAngel;
			g2d.fillArc(outerX + cordRadius * lengthBrick, outerY + cordRadius
					* lengthBrick,// 正方形左上角座標
					2 * outerR - 2 * cordRadius * lengthBrick, 2 * outerR - 2
							* cordRadius * lengthBrick,// 正方形的寬與高
					90 - cordAngel * degreeBrick, -degreeBrick);// 扇形的開始角度/ 終止角度/因為負角度是順時鐘
		}// end of paintBrick

		public void paintBrick(Graphics2D g2d, int cordAngel, int cordRadius,
				boolean isSiteBrick) {
			if (mapGame[cordAngel][cordRadius] == -100)
				g2d.setColor(Color.black);// 黑色.
			if (mapGame[cordAngel][cordRadius] == -90)
				g2d.setColor(nextBrickColor);// 綠色.
			if (mapGame[cordAngel][cordRadius] == -10)
				g2d.setColor(Color.red);// 紅色.
			cordAngel = cordAngel - rotateAngel;
			g2d.fillArc(outerX + cordRadius * lengthBrick, outerY + cordRadius
					* lengthBrick,// 正方形左上角座標
					2 * outerR - 2 * cordRadius * lengthBrick, 2 * outerR - 2
							* cordRadius * lengthBrick,// 正方形的寬與高
					90 - cordAngel * degreeBrick, -degreeBrick);// 扇形的開始角度/ 終止角度/因為負角度是順時鐘
			g2d.setColor(colorJPanel); // ***********注意, 這要與遊戲區域的背景色一致.
			// ***********以便造成消除的效果, 而呈現一個方塊.
			// 與前者一樣，只是徑向Y座標cordRadius都加1。
			g2d.fillArc(outerX + (cordRadius + 1) * lengthBrick, outerY
					+ (cordRadius + 1) * lengthBrick, 2 * outerR - 2 * (cordRadius + 1)
					* lengthBrick, 2 * outerR - 2 * (cordRadius + 1) * lengthBrick, 90
					- cordAngel * degreeBrick, -degreeBrick); // 因為負角度是順時鐘
		}// end of paintBrick

		public void paintBrickL(Graphics2D g2d, int cordAngel, int cordRadius,
				boolean isSiteBrickL) {
			if (mapGameL[cordAngel][cordRadius] == -100)
				g2d.setColor(Color.black);
			if (mapGameL[cordAngel][cordRadius] == -90)
				g2d.setColor(nextBrickColor);
			if (mapGameL[cordAngel][cordRadius] == -10)
				g2d.setColor(Color.red);
			if (!isSiteBrickL)
				cordAngel = (360 / degreeBrick) - 1 - cordAngel - rotateAngel;
			g2d.fillArc(outerX + cordRadius * lengthBrick, outerY + cordRadius
					* lengthBrick,// 正方形左上角座標
					2 * outerR - 2 * cordRadius * lengthBrick, 2 * outerR - 2
							* cordRadius * lengthBrick,// 正方形的寬與高
					90 - cordAngel * degreeBrick, -degreeBrick);// 扇形的開始角度/ 終止角度/因為負角度是順時鐘
			g2d.setColor(colorJPanel); // ***********注意, 這要與遊戲區域的背景色一致.
			// ***********以便造成消除的效果, 而呈現一個方塊.
			// 與前者一樣，只是徑向Y座標cordRadius都加1。
			g2d.fillArc(outerX + (cordRadius + 1) * lengthBrick, outerY
					+ (cordRadius + 1) * lengthBrick, 2 * outerR - 2 * (cordRadius + 1)
					* lengthBrick, 2 * outerR - 2 * (cordRadius + 1) * lengthBrick, 90
					- cordAngel * degreeBrick, -degreeBrick); // 因為負角度是順時鐘
		}// end of paintBrick

		void paintGameArea(Graphics2D g2d) {
			for (int cordAngel = startAngelRPlayer; cordAngel <= endAngelRPlayer - 1; cordAngel++) {
				for (int cordRadius = 0; cordRadius <= cordRadiusMax; cordRadius++) {
					if (mapGame[cordAngel][cordRadius] == -100) // 畫方塊
						paintBrick(g2d, cordAngel, cordRadius, false);
					if (mapGame[cordAngel][cordRadius] == -90) // 畫方塊
						paintBrick(g2d, cordAngel, cordRadius, false);
					if (mapGame[cordAngel][cordRadius] == -10) // 畫方塊
						paintBrick(g2d, cordAngel, cordRadius, false);
					if (mapGame[cordAngel][cordRadius] == -20) // 清除方塊
						clearBrick(g2d, cordAngel, cordRadius);
				}
			}

			for (int cordAngelL = startAngelLPlayer; cordAngelL <= endAngelLPlayer - 1; cordAngelL++) {
				for (int cordRadiusL = 0; cordRadiusL <= cordRadiusMax; cordRadiusL++) {
					if (mapGameL[cordAngelL][cordRadiusL] == -100) // 畫方塊
						paintBrickL(g2d, cordAngelL, cordRadiusL, false);
					if (mapGameL[cordAngelL][cordRadiusL] == -90) // 畫方塊
						paintBrickL(g2d, cordAngelL, cordRadiusL, false);
					if (mapGameL[cordAngelL][cordRadiusL] == -10) // 畫方塊
						paintBrickL(g2d, cordAngelL, cordRadiusL, false);
					if (mapGameL[cordAngelL][cordRadiusL] == -20) // 清除方塊
						clearBrickL(g2d, cordAngelL, cordRadiusL);
				}
			}
		}

		public void paintComponent(Graphics g) { // 必須override此方法 //預設雙緩衝
			super.paintComponent(g); // 清除畫面
			Graphics2D g2d = (Graphics2D) g;
			g2D = (Graphics2D) g;
			paintGameArea(g2d);
			paintSchelton(g2d);
		}

		int fallingAngelMax() {
			int cordAngel = currentBrickAngel;
			while (true) {
				for (int i = 0; i <= bricksFallingType[currentBrickType][currentBrickSubType].length - 1; ++i) {
					if (i == 0) // 第一組座標(i為0)是下落檢查方塊組中最底部的方塊, 其1st整數表示Angel座標.
						// 此if用以檢查是否到達mapGame底部
						if ((bricksFallingType[currentBrickType][currentBrickSubType][i][0] + cordAngel) > endAngelRPlayer - 1) { // fallingOver
							// =
							// true;
							return cordAngel;
						}
					int increAngel = bricksFallingType[currentBrickType][currentBrickSubType][i][0];
					int increRadius = bricksFallingType[currentBrickType][currentBrickSubType][i][1];
					if (mapGame[cordAngel + increAngel][currentBrickRadius + increRadius] == -10
							|| mapGame[cordAngel + increAngel][currentBrickRadius
									+ increRadius] == -40
					// || mapGame[cordAngel + increAngel][currentBrickRadius
					// + increRadius] == -90
					) {// fallingOver = true;
						return cordAngel;
					}

				}// End of for Loop
				++cordAngel;
			}// End of Infinite while Loop
			// return endAngelRPlayer-1; //可以掉落至mapGame底部.
		}// End of function fallingAngelMax()

		int fallingAngelMaxL() {
			int cordAngelL = currentBrickAngelL;
			while (true) {
				for (int i = 0; i <= bricksFallingType[currentBrickTypeL][currentBrickSubTypeL].length - 1; ++i) {
					if (i == 0) // 第一組座標(i為0)是下落檢查方塊組中最底部的方塊, 其1st整數表示Angel座標.
						// 此if用以檢查是否到達mapGame底部
						if ((bricksFallingType[currentBrickTypeL][currentBrickSubTypeL][i][0] + cordAngelL) > endAngelLPlayer - 1) { // fallingOver
							// =
							// true;
							return cordAngelL;
						}
					int increAngelL = bricksFallingType[currentBrickTypeL][currentBrickSubTypeL][i][0];
					int increRadiusL = bricksFallingType[currentBrickTypeL][currentBrickSubTypeL][i][1];
					if (mapGameL[cordAngelL + increAngelL][currentBrickRadiusL
							+ increRadiusL] == -10
							|| mapGameL[cordAngelL + increAngelL][currentBrickRadiusL
									+ increRadiusL] == -40) {// fallingOver = true;
						return cordAngelL;
					}

				}// End of for Loop
				++cordAngelL;
			}// End of Infinite while Loop
			// return endAngelRPlayer-1; //可以掉落至mapGame底部.
		}// End of function fallingAngelMax()

		void setRPlayerMoveBottom(int cordRPlayerBottom) {
			for (int i = 0; i <= bricksType[currentBrickType][currentBrickSubType].length - 1; ++i) {
				int increAngel = bricksType[currentBrickType][currentBrickSubType][i][0];
				int increRadius = bricksType[currentBrickType][currentBrickSubType][i][1];
				mapGame[currentBrickAngel + increAngel][currentBrickRadius
						+ increRadius] = -20;
			}
			for (int i = 0; i <= bricksType[currentBrickType][currentBrickSubType].length - 1; ++i) {
				int increAngel = bricksType[currentBrickType][currentBrickSubType][i][0];
				int increRadius = bricksType[currentBrickType][currentBrickSubType][i][1];
				if (RBrickCanNotChange)
					mapGame[cordRPlayerBottom + increAngel][currentBrickRadius
							+ increRadius] = -100;
				else if (isSpeedDownBrick)
					mapGame[cordRPlayerBottom + increAngel][currentBrickRadius
							+ increRadius] = -90;
				else
					mapGame[cordRPlayerBottom + increAngel][currentBrickRadius
							+ increRadius] = -10;
				// mapGame[cordRPlayerBottom + increAngel][currentBrickRadius
				// + increRadius] = -10;
			}
		}

		void setLPlayerMoveBottom(int cordRPlayerBottomL) {
			for (int i = 0; i <= bricksType[currentBrickTypeL][currentBrickSubTypeL].length - 1; ++i) {
				int increAngelL = bricksType[currentBrickTypeL][currentBrickSubTypeL][i][0];
				int increRadiusL = bricksType[currentBrickTypeL][currentBrickSubTypeL][i][1];
				mapGameL[currentBrickAngelL + increAngelL][currentBrickRadiusL
						+ increRadiusL] = -20;
			}
			for (int i = 0; i <= bricksType[currentBrickTypeL][currentBrickSubTypeL].length - 1; ++i) {
				int increAngelL = bricksType[currentBrickTypeL][currentBrickSubTypeL][i][0];
				int increRadiusL = bricksType[currentBrickTypeL][currentBrickSubTypeL][i][1];
				if (LBrickCanNotChange)
					mapGameL[cordRPlayerBottomL + increAngelL][currentBrickRadiusL
							+ increRadiusL] = -100;
				else if (isSpeedDownBrickL)
					mapGameL[cordRPlayerBottomL + increAngelL][currentBrickRadiusL
							+ increRadiusL] = -90;
				else
					mapGameL[cordRPlayerBottomL + increAngelL][currentBrickRadiusL
							+ increRadiusL] = -10;
			}
		}

		boolean moveLeftOK() {
			for (int i = 0; i <= bricksLeftType[currentBrickType][currentBrickSubType].length - 1; ++i) {
				if (i == 0)// 第一組座標(i為0)是左移檢查方塊組中最左邊的方塊, 其2nd整數表示Radiusl座標.
					// 此if用以檢查是否到達mapGame底部
					if ((bricksLeftType[currentBrickType][currentBrickSubType][i][1] + currentBrickRadius) > cordRadiusMax)
						return false;
				int increAngel = bricksLeftType[currentBrickType][currentBrickSubType][i][0];
				int increRadius = bricksLeftType[currentBrickType][currentBrickSubType][i][1];
				if (currentBrickRadius + increRadius >= 10)
					return false;
				if (mapGame[currentBrickAngel + increAngel][currentBrickRadius
						+ increRadius] == -10)
					return false;
			}
			return true;
		}

		boolean moveLeftOKL() {
			for (int i = 0; i <= bricksLeftType[currentBrickTypeL][currentBrickSubTypeL].length - 1; ++i) {
				if (i == 0)// 第一組座標(i為0)是左移檢查方塊組中最左邊的方塊, 其2nd整數表示Radiusl座標.
					// 此if用以檢查是否到達mapGame底部
					if ((bricksLeftType[currentBrickTypeL][currentBrickSubTypeL][i][1] + currentBrickRadiusL) > cordRadiusMax)
						return false;
				int increAngelL = bricksLeftType[currentBrickTypeL][currentBrickSubTypeL][i][0];
				int increRadiusL = bricksLeftType[currentBrickTypeL][currentBrickSubTypeL][i][1];
				if (currentBrickRadiusL + increRadiusL >= 10)
					return false;
				if (mapGameL[currentBrickAngelL + increAngelL][currentBrickRadiusL
						+ increRadiusL] == -10)
					return false;
			}
			return true;
		}

		void setRPlayerMoveLeft() {
			for (int i = 0; i <= bricksType[currentBrickType][currentBrickSubType].length - 1; ++i) {
				int increAngel = bricksType[currentBrickType][currentBrickSubType][i][0];
				int increRadius = bricksType[currentBrickType][currentBrickSubType][i][1];
				mapGame[currentBrickAngel + increAngel][currentBrickRadius
						+ increRadius] = -20;
			}
			++currentBrickRadius; // 設定方塊下落一格.
			for (int i = 0; i <= bricksType[currentBrickType][currentBrickSubType].length - 1; ++i) {
				int increAngel = bricksType[currentBrickType][currentBrickSubType][i][0];
				int increRadius = bricksType[currentBrickType][currentBrickSubType][i][1];
				if (RBrickCanNotChange)
					mapGame[currentBrickAngel + increAngel][currentBrickRadius
							+ increRadius] = -100;
				else if (isSpeedDownBrick)
					mapGame[currentBrickAngel + increAngel][currentBrickRadius
							+ increRadius] = -90;
				else
					mapGame[currentBrickAngel + increAngel][currentBrickRadius
							+ increRadius] = -10;
				// mapGame[currentBrickAngel + increAngel][currentBrickRadius
				// + increRadius] = -10;
			}
		}

		void setLPlayerMoveLeft() {
			// System.out.println("LEFT LEFT LEFT");
			for (int i = 0; i <= bricksType[currentBrickTypeL][currentBrickSubTypeL].length - 1; ++i) {
				int increAngelL = bricksType[currentBrickTypeL][currentBrickSubTypeL][i][0];
				int increRadiusL = bricksType[currentBrickTypeL][currentBrickSubTypeL][i][1];
				mapGameL[currentBrickAngelL + increAngelL][currentBrickRadiusL
						+ increRadiusL] = -20;
			}
			++currentBrickRadiusL; // 設定方塊下落一格.
			for (int i = 0; i <= bricksType[currentBrickTypeL][currentBrickSubTypeL].length - 1; ++i) {
				int increAngelL = bricksType[currentBrickTypeL][currentBrickSubTypeL][i][0];
				int increRadiusL = bricksType[currentBrickTypeL][currentBrickSubTypeL][i][1];
				if (LBrickCanNotChange)
					mapGameL[currentBrickAngelL + increAngelL][currentBrickRadiusL
							+ increRadiusL] = -100;
				else if (isSpeedDownBrickL)
					mapGameL[currentBrickAngelL + increAngelL][currentBrickRadiusL
							+ increRadiusL] = -90;
				else
					mapGameL[currentBrickAngelL + increAngelL][currentBrickRadiusL
							+ increRadiusL] = -10;
			}
		}

		void setRPlayerMoveRight() {
			for (int i = 0; i <= bricksType[currentBrickType][currentBrickSubType].length - 1; ++i) {
				int increAngel = bricksType[currentBrickType][currentBrickSubType][i][0];
				int increRadius = bricksType[currentBrickType][currentBrickSubType][i][1];
				mapGame[currentBrickAngel + increAngel][currentBrickRadius
						+ increRadius] = -20;
			}
			--currentBrickRadius; // 設定方塊下落一格.
			for (int i = 0; i <= bricksType[currentBrickType][currentBrickSubType].length - 1; ++i) {
				int increAngel = bricksType[currentBrickType][currentBrickSubType][i][0];
				int increRadius = bricksType[currentBrickType][currentBrickSubType][i][1];
				if (RBrickCanNotChange)
					mapGame[currentBrickAngel + increAngel][currentBrickRadius
							+ increRadius] = -100;
				else if (isSpeedDownBrick)
					mapGame[currentBrickAngel + increAngel][currentBrickRadius
							+ increRadius] = -90;
				else
					mapGame[currentBrickAngel + increAngel][currentBrickRadius
							+ increRadius] = -10;
				// mapGame[currentBrickAngel + increAngel][currentBrickRadius
				// + increRadius] = -10;
			}
		}

		void setLPlayerMoveRight() {
			System.out.println("RIGHTRIGHTRIGHT");
			for (int i = 0; i <= bricksType[currentBrickTypeL][currentBrickSubTypeL].length - 1; ++i) {
				int increAngelL = bricksType[currentBrickTypeL][currentBrickSubTypeL][i][0];
				int increRadiusL = bricksType[currentBrickTypeL][currentBrickSubTypeL][i][1];
				mapGameL[currentBrickAngelL + increAngelL][currentBrickRadiusL
						+ increRadiusL] = -20;
			}
			--currentBrickRadiusL; // 設定方塊下落一格.
			for (int i = 0; i <= bricksType[currentBrickTypeL][currentBrickSubTypeL].length - 1; ++i) {
				int increAngelL = bricksType[currentBrickTypeL][currentBrickSubTypeL][i][0];
				int increRadiusL = bricksType[currentBrickTypeL][currentBrickSubTypeL][i][1];
				if (LBrickCanNotChange)
					mapGameL[currentBrickAngelL + increAngelL][currentBrickRadiusL
							+ increRadiusL] = -100;
				else if (isSpeedDownBrickL)
					mapGameL[currentBrickAngelL + increAngelL][currentBrickRadiusL
							+ increRadiusL] = -90;
				else
					mapGameL[currentBrickAngelL + increAngelL][currentBrickRadiusL
							+ increRadiusL] = -10;
			}
		}

		boolean moveRightOK() {
			for (int i = 0; i <= bricksRightType[currentBrickType][currentBrickSubType].length - 1; ++i) {
				if (i == 0)// 第一組座標(i為0)是左移檢查方塊組中最右邊的方塊, 其2nd整數表示Radiusl座標.
					// 此if用以檢查是否到達mapGame底部
					if ((bricksRightType[currentBrickType][currentBrickSubType][i][1] + currentBrickRadius) < 0)
						return false;
				int increAngel = bricksRightType[currentBrickType][currentBrickSubType][i][0];
				int increRadius = bricksRightType[currentBrickType][currentBrickSubType][i][1];
				if (mapGame[currentBrickAngel + increAngel][currentBrickRadius
						+ increRadius] == -10)
					return false;
			}
			return true;
		}

		boolean moveRightOKL() {
			for (int i = 0; i <= bricksRightType[currentBrickTypeL][currentBrickSubTypeL].length - 1; ++i) {
				if (i == 0)// 第一組座標(i為0)是左移檢查方塊組中最右邊的方塊, 其2nd整數表示Radiusl座標.
					// 此if用以檢查是否到達mapGame底部
					if ((bricksRightType[currentBrickTypeL][currentBrickSubTypeL][i][1] + currentBrickRadiusL) < 0)
						return false;
				int increAngelL = bricksRightType[currentBrickTypeL][currentBrickSubTypeL][i][0];
				int increRadiusL = bricksRightType[currentBrickTypeL][currentBrickSubTypeL][i][1];
				if (mapGameL[currentBrickAngelL + increAngelL][currentBrickRadiusL
						+ increRadiusL] == -10)
					return false;
			}
			return true;
		}

		void recoverClear() {
			for (int i = 0; i <= bricksType[currentBrickType][currentBrickSubType].length - 1; ++i) {
				int increAngel = bricksType[currentBrickType][currentBrickSubType][i][0];
				int increRadius = bricksType[currentBrickType][currentBrickSubType][i][1];
				mapGame[currentBrickAngel + increAngel][currentBrickRadius
						+ increRadius] = -10;
			}
		}

		void recoverClearL() {
			for (int i = 0; i <= bricksType[currentBrickTypeL][currentBrickSubTypeL].length - 1; ++i) {
				int increAngelL = bricksType[currentBrickTypeL][currentBrickSubTypeL][i][0];
				int increRadiusL = bricksType[currentBrickTypeL][currentBrickSubTypeL][i][1];
				mapGameL[currentBrickAngelL + increAngelL][currentBrickRadiusL
						+ increRadiusL] = -10;
			}
		}

		void tempClear() {
			for (int i = 0; i <= bricksType[currentBrickType][currentBrickSubType].length - 1; ++i) {
				int increAngel = bricksType[currentBrickType][currentBrickSubType][i][0];
				int increRadius = bricksType[currentBrickType][currentBrickSubType][i][1];
				mapGame[currentBrickAngel + increAngel][currentBrickRadius
						+ increRadius] = 0;
			}
		}

		void tempClearL() {
			for (int i = 0; i <= bricksType[currentBrickTypeL][currentBrickSubTypeL].length - 1; ++i) {
				int increAngelL = bricksType[currentBrickTypeL][currentBrickSubTypeL][i][0];
				int increRadiusL = bricksType[currentBrickTypeL][currentBrickSubTypeL][i][1];
				mapGameL[currentBrickAngelL + increAngelL][currentBrickRadiusL
						+ increRadiusL] = 0;
			}
		}

		boolean rotateOK(int numberSubType) {
			tempClear();// 將目前的方塊先清除, 以免因與原來方塊重疊而誤判.
			int tempNextSubType = nextSubType(numberSubType);
			for (int i = 0; i <= bricksType[currentBrickType][tempNextSubType].length - 1; ++i) {
				int increAngel = bricksType[currentBrickType][tempNextSubType][i][0];
				int increRadius = bricksType[currentBrickType][tempNextSubType][i][1];
				if (currentBrickAngel + increAngel > endAngelRPlayer - 1) {
					// System.out.println("currentBrickAngel+increAngel >
					// endAngelRPlayer-1");
					recoverClear();
					return false;
				}
				if (currentBrickRadius + increRadius > cordRadiusMax
						|| currentBrickRadius + increRadius < 0) {
					// System.out.println("currentBrickRadius+increRadius > cordRadiusMax
					// || currentBrickRadius+increRadius < 0");
					recoverClear();
					return false;
				}
				if (mapGame[currentBrickAngel + increAngel][currentBrickRadius
						+ increRadius] == -10) {
					// System.out.println("mapGame[currentBrickAngel+increAngel][currentBrickRadius+increRadius]
					// == -10");
					recoverClear();
					return false;
				}
			}
			recoverClear();
			// System.out.println("rotateOK");
			return true;
		}

		boolean rotateOKL(int numberSubType) {
			tempClearL();// 將目前的方塊先清除, 以免因與原來方塊重疊而誤判.
			int tempNextSubType = nextSubTypeL(numberSubType);
			for (int i = 0; i <= bricksType[currentBrickTypeL][tempNextSubType].length - 1; ++i) {
				int increAngelL = bricksType[currentBrickTypeL][tempNextSubType][i][0];
				int increRadiusL = bricksType[currentBrickTypeL][tempNextSubType][i][1];
				if (currentBrickAngelL + increAngelL > endAngelLPlayer - 1) {
					// System.out.println("currentBrickAngel+increAngel >
					// endAngelRPlayer-1");
					recoverClearL();
					return false;
				}
				if (currentBrickRadiusL + increRadiusL > cordRadiusMax
						|| currentBrickRadiusL + increRadiusL < 0) {
					// System.out.println("currentBrickRadius+increRadius > cordRadiusMax
					// || currentBrickRadius+increRadius < 0");
					recoverClearL();
					return false;
				}
				if (mapGameL[currentBrickAngelL + increAngelL][currentBrickRadiusL
						+ increRadiusL] == -10) {
					// System.out.println("mapGame[currentBrickAngel+increAngel][currentBrickRadius+increRadius]
					// == -10");
					recoverClearL();
					return false;
				}
			}
			recoverClearL();
			// System.out.println("rotateOK");
			return true;
		}

		int nextSubType(int numberSubType) {
			int answer = currentBrickSubType + 1;
			if (numberSubType == 4)
				answer = answer % 4; // 循環變色.
			if (numberSubType == 2)
				answer = answer % 2; // 循環變色.
			// System.out.println("answer: " + answer);
			return answer;
		}

		int nextSubTypeL(int numberSubType) {
			int answer = currentBrickSubTypeL + 1;
			if (numberSubType == 4)
				answer = answer % 4; // 循環變色.
			if (numberSubType == 2)
				answer = answer % 2; // 循環變色.
			// System.out.println("answer: " + answer);
			return answer;
		}

		void rotateBrick() {
			int numberSubType = bricksType[currentBrickType].length;
			if (numberSubType == 1) {
				// System.out.println("numberSubType == 1");
				return; // 單一色不必有任何改變.
			}
			if (!rotateOK(numberSubType))
				return;
			int originalCurrentBrickSubType = currentBrickSubType;
			currentBrickSubType = nextSubType(numberSubType);
			// System.out.println("nextSubType: " + currentBrickSubType);
			for (int i = 0; i <= bricksType[currentBrickType][originalCurrentBrickSubType].length - 1; ++i) {
				int increAngel = bricksType[currentBrickType][originalCurrentBrickSubType][i][0];
				int increRadius = bricksType[currentBrickType][originalCurrentBrickSubType][i][1];
				mapGame[currentBrickAngel + increAngel][currentBrickRadius
						+ increRadius] = -20;
			}
			for (int i = 0; i <= bricksType[currentBrickType][currentBrickSubType].length - 1; ++i) {
				int increAngel = bricksType[currentBrickType][currentBrickSubType][i][0];
				int increRadius = bricksType[currentBrickType][currentBrickSubType][i][1];
				if (RBrickCanNotChange)
					mapGame[currentBrickAngel + increAngel][currentBrickRadius
							+ increRadius] = -100;
				else if (isSpeedDownBrick)
					mapGame[currentBrickAngel + increAngel][currentBrickRadius
							+ increRadius] = -90;
				else
					mapGame[currentBrickAngel + increAngel][currentBrickRadius
							+ increRadius] = -10;
				// mapGame[currentBrickAngel + increAngel][currentBrickRadius
				// + increRadius] = -10;
			}
		}

		void rotateBrickL() {
			int numberSubType = bricksType[currentBrickTypeL].length;
			if (numberSubType == 1) {
				// System.out.println("numberSubType == 1");
				return; // 單一色不必有任何改變.
			}
			if (!rotateOKL(numberSubType))
				return;
			int originalCurrentBrickSubType = currentBrickSubTypeL;
			currentBrickSubTypeL = nextSubTypeL(numberSubType);
			// System.out.println("nextSubType: " + currentBrickSubType);
			for (int i = 0; i <= bricksType[currentBrickTypeL][originalCurrentBrickSubType].length - 1; ++i) {
				int increAngelL = bricksType[currentBrickTypeL][originalCurrentBrickSubType][i][0];
				int increRadiusL = bricksType[currentBrickTypeL][originalCurrentBrickSubType][i][1];
				mapGameL[currentBrickAngelL + increAngelL][currentBrickRadiusL
						+ increRadiusL] = -20;
			}
			for (int i = 0; i <= bricksType[currentBrickTypeL][currentBrickSubTypeL].length - 1; ++i) {
				int increAngelL = bricksType[currentBrickTypeL][currentBrickSubTypeL][i][0];
				int increRadiusL = bricksType[currentBrickTypeL][currentBrickSubTypeL][i][1];
				if (LBrickCanNotChange)
					mapGameL[currentBrickAngelL + increAngelL][currentBrickRadiusL
							+ increRadiusL] = -100;
				else if (isSpeedDownBrickL)
					mapGameL[currentBrickAngelL + increAngelL][currentBrickRadiusL
							+ increRadiusL] = -90;
				else
					mapGameL[currentBrickAngelL + increAngelL][currentBrickRadiusL
							+ increRadiusL] = -10;
			}
		}

		public Tetris() {
			addKeyListener(new KeyAdapter() { // anonymous inner class
				public void keyPressed(KeyEvent ke) {
					int key = ke.getKeyCode();
					switch (key) {
					case KeyEvent.VK_UP: // 循環變換方塊的子型態.
						if (nowRPlayer && !setBrickNotInput) {
							rotateBrick();
							repaint();
							break;
						} else if (!setBrickNotInputL) {
							rotateBrickL();
							repaint();
							break;
						}
					case KeyEvent.VK_RIGHT: // 方塊往右移動
						if (nowRPlayer && !setBrickNotInput) {
							if (fallingOver)
								return;
							if (!moveRightOK()) {
								return;
							}
							setRPlayerMoveRight();
							repaint();
							break;
						} else if (!setBrickNotInputL) {
							if (fallingOverL)
								return;
							if (!moveLeftOKL()) {
								return;
							}
							setLPlayerMoveLeft();
							repaint();
							break;
						}
					case KeyEvent.VK_SPACE:
						// 右玩家交換方塊.
						if (nowRPlayer && !setBrickNotInput && !RPlayerChangLPlayer
								&& !RBrickCanNotChange) {
							RPlayerChangLPlayer = true;// 右玩家不可以一直換方塊.
							LBrickCanNotChange = true;// 右玩家換給左玩家的方塊, 左玩家不可以再換回來.
							// 以下for迴圈: 因為要換方塊, 所以先將目前方塊消除.
							for (int i = 0; i <= bricksType[currentBrickType][currentBrickSubType].length - 1; ++i) {
								int increAngel = bricksType[currentBrickType][currentBrickSubType][i][0];
								int increRadius = bricksType[currentBrickType][currentBrickSubType][i][1];
								mapGame[currentBrickAngel + increAngel][currentBrickRadius
										+ increRadius] = -20;
							}
							// 以下將左玩家的下一方塊與右玩家的目前方塊交換.
							int temp = currentBrickType;// 換方塊型態.
							currentBrickType = nextCurrentBrickTypeL;
							nextCurrentBrickTypeL = temp;
							boolean temp1 = isSpeedDownBrick;// 換降速度方塊.
							isSpeedDownBrick = nextIsSpeedDownBrickL;
							nextIsSpeedDownBrickL = temp1;
							currentBrickSubType = 0;
							currentBrickRotateType = 0;// 將新方塊放置到頂端.
							currentBrickAngel = startAngelRPlayer + (2 - 1) + newBrickIncre;
							currentBrickRadius = cordRadiusMax / 2;
						}
						// 左玩家交換方塊.
						else if (!setBrickNotInputL && !LPlayerChangRPlayer
								&& !LBrickCanNotChange) {
							LPlayerChangRPlayer = true;// 左玩家不可以一直換方塊.
							RBrickCanNotChange = true;// 左玩家換給右玩家的方塊, 右玩家不可以再換回來.
							for (int i = 0; i <= bricksType[currentBrickTypeL][currentBrickSubTypeL].length - 1; ++i) {
								int increAngel = bricksType[currentBrickTypeL][currentBrickSubTypeL][i][0];
								int increRadius = bricksType[currentBrickTypeL][currentBrickSubTypeL][i][1];
								mapGameL[currentBrickAngelL + increAngel][currentBrickRadiusL
										+ increRadius] = -20;
							}
							// 以下將左玩家的下一方塊與右玩家的目前方塊交換.
							int temp = currentBrickTypeL;// 換方塊型態.
							currentBrickTypeL = nextCurrentBrickType;
							nextCurrentBrickType = temp;
							boolean temp1 = isSpeedDownBrickL;// 換降速度方塊.
							isSpeedDownBrickL = nextIsSpeedDownBrick;
							nextIsSpeedDownBrick = temp1;
							currentBrickSubTypeL = 0;
							currentBrickRotateTypeL = 0;// 將新方塊放置到頂端.
							currentBrickAngelL = startAngelLPlayer + (2 - 1) + newBrickIncreL;
							currentBrickRadiusL = cordRadiusMax / 2;
						}
						break;
					case KeyEvent.VK_DOWN: // 讓方塊快速下落
						if (nowRPlayer && !setBrickNotInput) {
							int cordRPlayerBottom = fallingAngelMax(); // 找到可以直接下落的最低位置.
							setRPlayerMoveBottom(cordRPlayerBottom); //
							currentBrickAngel = endAngelRPlayer - 1; // 設定currentBrickAngel是為了結束一個方塊的週期.
							repaint(); // 以免發生延遲現象.
							break;
						} else if (!setBrickNotInputL) {
							int cordRPlayerBottomL = fallingAngelMaxL(); // 找到可以直接下落的最低位置.
							setLPlayerMoveBottom(cordRPlayerBottomL); //
							currentBrickAngelL = endAngelLPlayer - 1; // 設定currentBrickAngel是為了結束一個方塊的週期.
							repaint(); // 以免發生延遲現象.
							break;
						}
					case KeyEvent.VK_LEFT: // 方塊往左移動
						if (nowRPlayer && !setBrickNotInput) {
							if (fallingOver)
								return;
							if (!moveLeftOK()) {
								return;
							}
							setRPlayerMoveLeft();
							repaint();
							break;
						} else if (!setBrickNotInputL) {
							if (fallingOverL)
								return;
							if (!moveRightOKL()) {
								return;
							}
							setLPlayerMoveRight();
							repaint();
							break;
						}
					}
				}
			} // end anonymous inner class
			); // end call to addActionListener
		}

		int myStartAngelRPlayer, myEndAngelRPlayer;

		void toSite() {
			if (rotateIndex >= 7 && rotateIndex <= 11) {
				brickToSite = true;
				for (int i = 0; i <= bricksType[currentBrickType][currentBrickSubType].length - 1; ++i) {
					int increAngel = bricksType[currentBrickType][currentBrickSubType][i][0];
					int increRadius = bricksType[currentBrickType][currentBrickSubType][i][1];
					mapGame[currentBrickAngel + increAngel][currentBrickRadius
							+ increRadius] = -20;
				}
				// paintSiteBrick();
			}
		}

		int siteCurrentBrickAngel = startAngelRPlayer;

		int siteCurrentBrickRadius = numTrack1 - (4 - 1);

		void setNextBrickColor(Graphics2D g2d, boolean isSpeedDown,
				boolean nextBrick) {
			if (nextBrick && LPlayerChangRPlayer && !nowRPlayer)
				g2d.setColor(Color.black);
			else if (isSpeedDown)
				g2d.setColor(nextBrickColor);
			else
				g2d.setColor(Color.red);
		}

		void paintNextBrick(Graphics2D g2d) {
			setNextBrickColor(g2d, nextIsSpeedDownBrick, true);
			for (int i = 0; i <= bricksType[nextCurrentBrickType][nextCurrentBrickSubType].length - 1; ++i) {
				int increAngel = bricksType[nextCurrentBrickType][nextCurrentBrickSubType][i][0];
				int increRadius = bricksType[nextCurrentBrickType][nextCurrentBrickSubType][i][1];
				g2d.fill3DRect(710 + 15 * (increAngel), 490 + 15 * (increRadius), 15,
						15, true);
			}
			setNextBrickColor(g2d, next2IsSpeedDownBrick, false);
			for (int i = 0; i <= bricksType[next2CurrentBrickType][next2CurrentBrickSubType].length - 1; ++i) {
				int increAngel = bricksType[next2CurrentBrickType][next2CurrentBrickSubType][i][0];
				int increRadius = bricksType[next2CurrentBrickType][next2CurrentBrickSubType][i][1];
				g2d.fill3DRect(710 + 15 * (increAngel), 400 + 15 * (increRadius), 15,
						15, true);
			}
			setNextBrickColor(g2d, next3IsSpeedDownBrick, false);
			for (int i = 0; i <= bricksType[next3CurrentBrickType][next3CurrentBrickSubType].length - 1; ++i) {
				int increAngel = bricksType[next3CurrentBrickType][next3CurrentBrickSubType][i][0];
				int increRadius = bricksType[next3CurrentBrickType][next3CurrentBrickSubType][i][1];
				g2d.fill3DRect(710 + 15 * (increAngel), 320 + 15 * (increRadius), 15,
						15, true);
			}
		}

		void setNextBrickColorL(Graphics2D g2d, boolean isSpeedDown,
				boolean nextBrick) {
			if (nextBrick && RPlayerChangLPlayer && nowRPlayer)
				g2d.setColor(Color.black);
			else if (isSpeedDown)
				g2d.setColor(nextBrickColor);
			else
				g2d.setColor(Color.red);
		}

		void paintNextBrickL(Graphics2D g2d) {
			setNextBrickColorL(g2d, nextIsSpeedDownBrickL, true);
			for (int i = 0; i <= bricksType[nextCurrentBrickTypeL][nextCurrentBrickSubTypeL].length - 1; ++i) {
				int increAngel = bricksType[nextCurrentBrickTypeL][nextCurrentBrickSubTypeL][i][0];
				int increRadius = bricksType[nextCurrentBrickTypeL][nextCurrentBrickSubTypeL][i][1];
				g2d.fill3DRect(710 + 15 * (increAngel), 220 + 15 * (increRadius), 15,
						15, true);
			}
			setNextBrickColorL(g2d, next2IsSpeedDownBrickL, false);
			for (int i = 0; i <= bricksType[next2CurrentBrickTypeL][next2CurrentBrickSubTypeL].length - 1; ++i) {
				int increAngel = bricksType[next2CurrentBrickTypeL][next2CurrentBrickSubTypeL][i][0];
				int increRadius = bricksType[next2CurrentBrickTypeL][next2CurrentBrickSubTypeL][i][1];
				g2d.fill3DRect(710 + 15 * (increAngel), 140 + 15 * (increRadius), 15,
						15, true);
			}
			setNextBrickColorL(g2d, next3IsSpeedDownBrickL, false);
			for (int i = 0; i <= bricksType[next3CurrentBrickTypeL][next3CurrentBrickSubTypeL].length - 1; ++i) {
				int increAngel = bricksType[next3CurrentBrickTypeL][next3CurrentBrickSubTypeL][i][0];
				int increRadius = bricksType[next3CurrentBrickTypeL][next3CurrentBrickSubTypeL][i][1];
				g2d.fill3DRect(710 + 15 * (increAngel), 60 + 15 * (increRadius), 15,
						15, true);
			}
		}

		public void paintSchelton(Graphics2D g2d) { // 繪製骨架 直線與同心圓
			paintNextBrick(g2d);
			g2d.setStroke(new BasicStroke(8f));
			g2d.setColor(Color.blue);
			g2d.drawLine(680, 280, 770, 280);
			paintNextBrickL(g2d);
			myStartAngelRPlayer = startAngelRPlayer - rotateAngel;
			myEndAngelRPlayer = endAngelRPlayer - rotateAngel;

			if (myStartAngelRPlayer < 0) {
				while (true) {
					myStartAngelRPlayer += numLine + 1;
					if (myStartAngelRPlayer >= 0)
						break;
				}
			}
			if (myEndAngelRPlayer < 0) {
				while (true) {
					myEndAngelRPlayer += numLine + 1;
					if (myEndAngelRPlayer >= 0)
						break;
				}
			}

			if (myStartAngelRPlayer > 35) {
				while (true) {
					myStartAngelRPlayer -= numLine + 1;
					if (myStartAngelRPlayer <= 35)
						break;
				}
			}
			if (myEndAngelRPlayer > 35) {
				while (true) {
					myEndAngelRPlayer -= numLine + 1;
					if (myEndAngelRPlayer <= 35)
						break;
				}
			}

			g2d.setColor(colorTrack); // 設定線條平滑地畫出
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setStroke(new BasicStroke(1.5f));
			for (int i = 0; i <= numLine; i++) { // 繪製直線
				if (i == myStartAngelRPlayer || i == myEndAngelRPlayer) {
					g2d.setStroke(new BasicStroke(3f));
					g2d.setColor(Color.red);
				}
				g2d.drawLine(outerIncreLineXArray[i], outerIncreLineYArray[i],
						innerIncreLineXArray[i], innerIncreLineYArray[i]);// 右半邊的直線
				g2d.drawLine(outerIncreLineXArray1[i], outerIncreLineYArray1[i],
						innerIncreLineXArray1[i], innerIncreLineYArray1[i]);// 左半邊的直線
				if (i == myStartAngelRPlayer || i == myEndAngelRPlayer) {
					g2d.setStroke(new BasicStroke(1.5f));
					g2d.setColor(colorTrack);
				}// 上一行恢復筆觸與顏色
			}
			g2d.setStroke(new BasicStroke(1.5f)); // 同心圓的筆觸.
			for (int i = 0; i <= numTrack; i++) { // 控制同心圓繪製的數目
				g2d.drawOval(ovalCordX[i], ovalCordY[i], ovalCordL[i], ovalCordW[i]);
			}
			// ************可能不必要的code*********** 繪製此最內層圓的目的是為了避免可能的閃爍
			g2d.setColor(colorJPanel); // 使用面板顏色.
			g2d.fillOval(outerX + lengthBrick * numTrack1, outerY + lengthBrick
					* numTrack1, 2 * outerR - 2 * lengthBrick * numTrack1, 2 * outerR - 2
					* lengthBrick * numTrack1);
			// 最內層同心圓的線條加粗, 並且改變顏色.
			g2d.setColor(Color.yellow);
			g2d.setStroke(new BasicStroke(3f));
			g2d.fillOval(outerX + lengthBrick * numTrack1, outerY + lengthBrick
					* numTrack1, 2 * outerR - 2 * lengthBrick * numTrack1, 2 * outerR - 2
					* lengthBrick * numTrack1);
			// 方塊玩法變化區域
			// if(brickInSite) paintSiteBrick();
			for (int i = 0; i <= 4; ++i) {
				g2d.setStroke(new BasicStroke(1.5f));
				g2d.setColor(Color.green);
				g2d.drawLine(siteouterIncreLineXArray[i], siteouterIncreLineYArray[i],
						siteinnerIncreLineXArray[i], siteinnerIncreLineYArray[i]);// 右半邊的直線
				g2d.setColor(Color.blue);
				g2d.drawLine(siteouterIncreLineXArray1[i],
						siteouterIncreLineYArray1[i], siteinnerIncreLineXArray1[i],
						siteinnerIncreLineYArray1[i]);// 左半邊的直線
				g2d.setColor(Color.green);
				g2d.drawLine(site2outerIncreLineXArray[i],
						site2outerIncreLineYArray[i], site2innerIncreLineXArray[i],
						site2innerIncreLineYArray[i]);// 右半邊的直線
				g2d.setColor(Color.blue);
				g2d.drawLine(site2outerIncreLineXArray1[i],
						site2outerIncreLineYArray1[i], site2innerIncreLineXArray1[i],
						site2innerIncreLineYArray1[i]);// 左半邊的直線
				g2d.setColor(colorTrack);
			}
			for (int i = numTrack; i <= numTrack1; i++) { // 控制同心圓繪製的數目
				g2d.setColor(Color.green);
				int tempStart = ((90 / degreeBrick) - (startAngelRPlayer - 1) - 4);
				g2d.drawArc(ovalCordX[i], ovalCordY[i], ovalCordL[i], ovalCordW[i],
						tempStart * degreeBrick, degreeBrick * 4);// 右上
				int temp = endAngelRPlayer - startAngelRPlayer;
				g2d.drawArc(ovalCordX[i], ovalCordY[i], ovalCordL[i], ovalCordW[i], 360
						- (temp / 2 - 1) * degreeBrick, degreeBrick * 4);// 右下
				g2d.setColor(Color.blue);
				g2d.drawArc(ovalCordX[i], ovalCordY[i], ovalCordL[i], ovalCordW[i],
						tempStart * degreeBrick + 180, degreeBrick * 4);// 左下
				g2d.drawArc(ovalCordX[i], ovalCordY[i], ovalCordL[i], ovalCordW[i], 360
						- (temp / 2 - 1) * degreeBrick + 180, degreeBrick * 4);// 左上
				g2d.setColor(colorTrack);
			}
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_OFF);

			
//			g2d.setColor(Color.black);
//			setFont(new Font("Arial", Font.BOLD, 12));
//			for (int i = 0; i <= numLine*2+1; i++) {
//				g2d.drawString(Integer.toString(10), XArray[i], YArray[i]);					
//			}
//			for (int i = 0; i <= numLine*2+1; i++) {
//				for(int j = 0; j <= 3; ++j) {
//					g2d.drawString(Integer.toString(9-j), 	XDecre1[i][j], YDecre1[i][j]);
//				}
//			}
//			for (int i = 0; i <= numLine*2+1; i++) {
//				for(int j = 0; j <= 1; ++j) {
//					g2d.drawString(Integer.toString(5-j), XDecre2[i][j], YDecre2[i][j]);
//				}
//			}
//			for (int i = 0; i <= numLine*2+1; i++) {
//				for(int j = 0; j <= 2; ++j) {
//					g2d.drawString(Integer.toString(3-j), XDecre3[i][j], YDecre3[i][j]);
//				}
//			}

			
		}// End of paintSchelton
	}//End of Tetris
}// End of Class

//String msg = "";
//String FontList[];
//GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//FontList = ge.getAvailableFontFamilyNames();
//for(int i = 0; i< FontList.length; ++i)
//	msg += FontList[i] + "  ";
//g2d.drawString(msg, 4, 16);
//if(1==1) return;

