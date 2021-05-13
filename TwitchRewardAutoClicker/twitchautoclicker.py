import datetime
from PIL.ImageOps import grayscale
import pyautogui
import time
import keyboard
import random
import win32api, win32con
import sys
from PIL import ImageGrab
from functools import partial

ImageGrab.grab = partial(ImageGrab.grab, all_screens=True)

""" The program is autoclicking the reward button on twitch.tv using the win32api + pyautogui. Program is stopped with holding 
the escape key(could take up to 5 seconds because of the sleep function to reduce system usage(can be modified)), 
or it can be killed normally in terminal.  --author Danko Čurlin"""


def countdown(t):
    while t:
        mins, secs = divmod(t, 60)
        timer = "{:02d}".format(secs)
        print("Starting in ", timer, end="\r")
        time.sleep(1)
        t -= 1


# bot actions
def botStart():
    start_time = time.time()
    collected = 0
    StreamerGone = 0
    while keyboard.is_pressed("esc") == False:
        if pyautogui.locateOnScreen("rewardButton.png", grayscale=True) != None:
            try:
                coordinates = pyautogui.locateOnScreen(
                    "rewardButton.png", grayscale=True
                )
            except TypeError:
                time.sleep(2)
                continue
            # randomising key pressing to avoid possible bot detection
            try:
                x = random.randint(
                    int(coordinates[0]), int(coordinates[0] + coordinates[2])
                )
                y = random.randint(
                    int(coordinates[1]), int(coordinates[1] + coordinates[3])
                )
            except TypeError:
                time.sleep(2)
                continue
            click(x, y)
            time.sleep(0.5)
            click(x, y - 45)
            time.sleep(4)
            StreamerGone = 0
            collected += 50
            print("Collected points >> ", collected)
            print(
                "Running time     >> "
                + str((time.time() - start_time) / 60)
                + " minutes"
            )
            print(
                "Current average  >> ",
                str(collected / ((time.time() - start_time) / 60)) + " ppm",
            )
            print("#############################################################")
        else:
            StreamerGone += 4
            # ending if passed one hour without a reward
            if StreamerGone == 3600:
                break
            else:
                time.sleep(4)


# clicking
def click(x, y):
    win32api.SetCursorPos((x, y))
    win32api.mouse_event(win32con.MOUSEEVENTF_LEFTDOWN, 0, 0)
    win32api.mouse_event(win32con.MOUSEEVENTF_LEFTUP, 0, 0)


def start():
    countdown(3)
    print("\nRunning...")
    botStart()


start()