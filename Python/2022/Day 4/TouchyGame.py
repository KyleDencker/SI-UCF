import pygame
import math
import random
import time

pygame.init()

screen = pygame.display.set_mode( [500, 500] )

running = True
blue = 255;

score = 0

timer = 0
last_time = time.monotonic()
start = False

dot_list = [ [250, 250, 20] ]

while running == True:
    

    # Events (actions the user takes)
    for event in pygame.event.get():
        if (event.type == pygame.QUIT):
            running = False

        if event.type == pygame.MOUSEBUTTONUP:
            pos = pygame.mouse.get_pos()

            for dot in dot_list:
                if (math.sqrt((dot[0]-pos[0]) ** 2 + (dot[1]-pos[1])**2) <= dot[2]):
                    # if I have touched a dot...
                    start = True

                    for i in range(2):
                        x = random.randint(10, 490)
                        y = random.randint(10, 490)
                        r = random.randint(3, 15)
                        item = [x, y, r]
                        dot_list.append(item)
                    score+=21-dot[2]
                    dot_list.remove(dot)
                    
        if event.type == pygame.KEYDOWN:

            if event.key == pygame.K_c:
                print("touchy touch c")
            if event.key == pygame.K_s:
                print("Touchy touchy Score: " + str(score))

    current_time = time.monotonic()

    diff_time = current_time - last_time
    if (start):
        timer += diff_time
    last_time = current_time

    print(timer)
    if timer > 10:
        running = False
        print("Your score is " + str(score))


    screen.fill( (math.fabs(blue), 0, 0) )
    blue += 10;
    if (blue > 255):
        blue = -255
    for dot in dot_list:
        pygame.draw.circle( screen, (255, 0, 0), (dot[0], dot[1]), dot[2])

    pygame.display.flip()

pygame.quit()
