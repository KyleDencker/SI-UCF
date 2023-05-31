# Kyle Dencker

import os.path
import pygame
import math
import random
import time

import Player
import BadGuy
import PowerUp


    




def update(time):
    global total_time
    global interval
    global next_spawn, high_score
    global power_spawn, score

    score += time * player.get_size()
    
    if (key_input['up']):
        player.update(0, -player.get_speed())
    if (key_input['down']):
        player.update(0, player.get_speed())
    if (key_input['left']):
        player.update(-player.get_speed(), 0)
    if (key_input['right']):
        player.update(player.get_speed(), 0)
    
    for bg in badguys:
        bg.update()
        if bg.is_dead():
            badguys.remove(bg)

        # Player death
        if (bg.get_hitbox().colliderect(player.get_hitbox())):
            player.hit()
            badguys.remove(bg)
            if (player.get_lives() <= 0):
                player.reset()
                interval = 0
                next_spawn = 1
                if (high_score < round(score)):
                    high_score = round(score)
                score = 0
                total_time = 0

                badguys.clear()
            
            
    for p in power_list:
        if (p.get_hitbox().colliderect(player.get_hitbox())):
            if (p.get_type() == 1):
                player.add_speed()
            if (p.get_type() == 2):
                player.get_small()
            if (p.get_type() == 3):
                player.get_big()
            if (p.get_type() == 4):
                player.add_life()
            power_list.remove(p)

    if (interval > next_spawn):
        badguys.append(BadGuy.Badguy())
        interval = 0
        next_spawn = 5 - math.sqrt(total_time)
        if (next_spawn < .5):
            next_spawn = .5
    if (power_spawn > 2):
        power_spawn -= 2
        power_list.append(PowerUp.PowerUp())
    

def draw(s):
    global total_time, high_score, score
    s.fill ( (255, 255, 255) )
    
    for bg in badguys:
        bg.draw(s)
    for p in power_list:
        p.draw(s)
    player.draw(s)
    text = font.render("Score: " + str(round(score)) + " High Score: " + str(high_score), True, (0, 0, 0))
    s.blit(text, (20, 20))

def main():

    global player
    global badguys
    global key_input
    global total_time
    global interval
    global next_spawn
    global power_list
    global power_spawn
    global font
    global high_score, score

    if (os.path.exists("highscores.txt")):

        filein = open("highscores.txt", "r")
        high_score = int(filein.read())
        filein.close()
    else:
        high_score = 0
        
    next_spawn = 1
    score = 0

    player = Player.Player()
    badguys = []
    power_list = []
    total_time = 0
    interval = 0
    power_spawn = 0

    key_input = {
        "up": False,
        "down": False,
        "left": False,
        "right": False
    }

    
    
    pygame.init()
    font = pygame.font.SysFont("comicsansms", 30)

    screen = pygame.display.set_mode( [500, 500] )
    running = True
    last_time = time.monotonic()

    while running == True:
        
        # Events (actions the user takes)
        for event in pygame.event.get():
            if (event.type == pygame.QUIT):
                running = False
                fileout = open("highscores.txt", "w")
                fileout.write(str(high_score))
                fileout.close()
            if event.type == pygame.MOUSEBUTTONUP:
                pass
                        
            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_LEFT:
                    key_input['left'] = True
                if event.key == pygame.K_RIGHT:
                    key_input['right'] = True
                if event.key == pygame.K_UP:
                    key_input['up'] = True
                if event.key == pygame.K_DOWN:
                    key_input['down'] = True
                if event.key == pygame.K_s:
                    player.reduce_speed()
            if event.type == pygame.KEYUP:
                if event.key == pygame.K_LEFT:
                    key_input['left'] = False
                if event.key == pygame.K_RIGHT:
                    key_input['right'] = False
                if event.key == pygame.K_UP:
                    key_input['up'] = False
                if event.key == pygame.K_DOWN:
                    key_input['down'] = False
            
        current_time = time.monotonic()
        diff_time = current_time - last_time
        last_time = current_time
        interval += diff_time
        total_time += diff_time
        power_spawn += diff_time
        
        update(diff_time)
        draw(screen)
        
        pygame.display.flip()
    pygame.quit()

main()
