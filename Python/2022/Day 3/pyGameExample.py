import pygame
import math

pygame.init()

screen = pygame.display.set_mode( [500, 500] )

running = True
blue = 255;

while running == True:

    # Events (actions the user takes)
    for event in pygame.event.get():
        if (event.type == pygame.QUIT):
            running = False

        if event.type == pygame.MOUSEBUTTONUP:
            pos = pygame.mouse.get_pos()
            print(pos)
            print("X" + str(pos[0]))
            if (pos[0] >= 150 and pos[0] <= 350):
                print("We are inside the circle")


    screen.fill( (0, 0, math.fabs(blue)) )
    blue += 5;
    if (blue > 255):
        blue = -255

    pygame.draw.circle( screen, (255, 0, 0), (250, 250), 100)
    pygame.draw.rect(screen, (255, 255, 255), (250, 150, 10, 100))

    pygame.display.flip()

pygame.quit()
