import datetime
import pyautogui
import time
import keyboard
import random
import win32api, win32con


""" The program is autoclicking the reward button on twitch.tv, upon starting the program when pressed
number 1 it will use standard coordinates of a button(1080p resolution), if needed to put custom ones
press q, put the cursor on top left side of the button and apply it with enter key, do the same thing with 
bottom right corner.  Program is stopped with holding the escape key(could take up to 30 seconds because of
sleep function to reduce system usage, can be stopped with 'x' button too.  -- @author Danko Čurlin"""


#defining standard position of the button
global testvar
testvar = 'q'
global topleftX
topleftX = 1669
global bottomrightX
bottomrightX = 1683
global topleftY
topleftY = 1004
global bottomrightY
bottomrightY = 1025

def countdown(t): 
	while t: 
		mins, secs = divmod(t, 60) 
		timer = '{:02d}'.format(secs) 
		print("Starting in ", timer, end="\r") 
		time.sleep(1) 
		t -= 1

#bot actions
def botStart():
    start_time = time.time()
    collected = 0
    StreamerGone = 0
    while keyboard.is_pressed('esc') == False:
        if pyautogui.locateOnScreen('rewardButton.png') != None:
            StreamerGone = 0
            collected += 50
            print("Collected points >> ", collected)
            print("Running time     >> " + str((time.time() - start_time)/60) + " minutes")
            print("#############################################################")
            print("Current average  >> ", str(collected / ((time.time() - start_time) / 60)) + " ppm")
            print("#############################################################")
            #randomising key pressing
            clickyclick(random.randint(topleftX, bottomrightX), random.randint(topleftY, bottomrightY))
            time.sleep(0.5)
            moveymove()
            time.sleep(5)
        else:
            StreamerGone += 1
            #ending if passed one hour without a reward
            if (StreamerGone == 120):
                break
            else:
                time.sleep(30)

#clicking
def clickyclick(x, y):
    win32api.SetCursorPos((x,y))
    win32api.mouse_event(win32con.MOUSEEVENTF_LEFTDOWN, 0, 0)
    win32api.mouse_event(win32con.MOUSEEVENTF_LEFTUP, 0, 0)

#reseting the mouse position because if its left hovering on button, pyautogui can not find the button
def moveymove():
    win32api.SetCursorPos((1000, 500))



#type functions
def goStandard():
    countdown(3)
    print("\nRunning...")
    botStart()

def goCustom():
    print("Define a rectangle with hovering over edges of a square and pressing on letter 'q', try to be as close as possible")
    testinput = input("Click on top left edge of the square >> ")
    if(testinput == testvar):
        topleftX, topleftY = pyautogui.position()
        print(topleftX, topleftY)
        testinput = None

    testinput = input("Click on bottom right edge of the square >> ")
    if(testinput == testvar):
        bottomrightX, bottomrightY = pyautogui.position()
        print(bottomrightX, bottomrightY)
        testinput = None
    
    countdown(3)
    print("\nRunning...")
    botStart()

#start
type = input("Type 1 if you want standard layout and type 2 if you want custom button placement > ")
print("Stopping the program with holding 'esc'")
#continuing the program
if (type == '1'):
    goStandard()
elif(type == '2'):
    goCustom()
else:
    print("Incorrect value, please input 1 or 2")
