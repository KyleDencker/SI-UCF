# Kyle Dencker

import pygame
import math
import random
import time


class Player:

    def __init__(self):
        self.hitbox = pygame.Rect(225, 225, 50, 50)
        self.speed = 5

    def update(self, x, y):
        self.hitbox.x += x
        self.hitbox.y += y

    def draw(self, s):
        pygame.draw.rect(s, (0, 0, 0), self.hitbox)

    def get_speed(self):
        return self.speed

    def reset(self):
        self.hitbox = pygame.Rect(225, 225, 50, 50)
        self.speed = 5
    def get_hitbox(self):
        return self.hitbox

class Badguy:

    def __init__(self, x, y):
        self.hitbox = pygame.Rect(x, y, 50, 50)

    def draw(self, s):
        pygame.draw.rect(s, (255, 0, 0), self.hitbox)

    def get_hitbox(self):
        return self.hitbox



def update(time):
    for bg in badguys:
        if (bg.get_hitbox().colliderect(player.get_hitbox())):
            player.reset()

def draw(s):
    s.fill ( (255, 255, 255) )
    player.draw(s)
    for bg in badguys:
        bg.draw(s)

def main():

    global player
    global badguys

    player = Player()
    badguys = []

    badguys.append( Badguy(0, 0) )
    badguys.append( Badguy(100, 200) )
    badguys.append( Badguy(300, 0) )
    badguys.append( Badguy(50, 100) )
    
    pygame.init()
    screen = pygame.display.set_mode( [500, 500] )
    running = True
    last_time = time.monotonic()

    while running == True:
        
        # Events (actions the user takes)
        for event in pygame.event.get():
            if (event.type == pygame.QUIT):
                running = False
            if event.type == pygame.MOUSEBUTTONUP:
                pass
                        
            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_LEFT:
                    player.update(-player.get_speed(), 0)
                if event.key == pygame.K_RIGHT:
                    player.update(player.get_speed(), 0)
            
        current_time = time.monotonic()
        diff_time = current_time - last_time
        last_time = current_time
        update(diff_time)
        draw(screen)
        
        pygame.display.flip()
    pygame.quit()

main()
