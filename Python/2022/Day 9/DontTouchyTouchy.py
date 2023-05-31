# Kyle Dencker

import os.path
import pygame
import math
import random
import time


class Player:



    def __init__(self):
        self.hitbox = pygame.Rect(225, 225, 50, 50)
        self.speed = 5
        self.lives = 1
        self.angle = 0

    def update(self, x, y):
        self.hitbox.x += x
        self.hitbox.y += y
        if (self.hitbox.x < -self.hitbox.width):
            self.hitbox.x = 500
        if (self.hitbox.x > 500):
            self.hitbox.x = - self.hitbox.width
        if (self.hitbox.y < -self.hitbox.height):
            self.hitbox.y = 500
        if (self.hitbox.y > 500):
            self.hitbox.y = -self.hitbox.height
        
        

    def draw(self, s):
        self.angle += 5
        pygame.draw.rect(s, (0, 0, 0), self.hitbox)
        extra = self.lives - 1
        if (extra > 0):
            spacing = 360 / extra
            center = [self.hitbox.x + self.hitbox.width/2, self.hitbox.y + self.hitbox.height/2]
            for num in range(extra):
                x = center[0] + math.cos(math.radians(self.angle+num * spacing)) * (self.hitbox.width + 10)
                y = center[1] + math.sin(math.radians(self.angle+num * spacing)) * (self.hitbox.width + 10)
                pygame.draw.rect(s, (100, 100, 100), (x-self.hitbox.width/10, y-self.hitbox.width/10, self.hitbox.width/5, self.hitbox.width/5))

    def get_speed(self):
        return self.speed
    def add_speed(self):
        self.speed+=1
    def reduce_speed(self):
        self.speed -= 1
        self.speed = max(self.speed, 5)
    def get_small(self):
        self.hitbox.width -= 5
        self.hitbox.height -= 5
        if (self.hitbox.width < 5):
            self.hitbox.width = 5
            self.hitbox.height = 5
    def get_big(self):
        self.hitbox.width += 5
        self.hitbox.height += 5
    def reset(self):
        self.hitbox = pygame.Rect(225, 225, 50, 50)
        self.speed = 5
        self.lives = 1
    def get_hitbox(self):
        return self.hitbox
    
    def get_size(self):
        return self.hitbox.width
    def get_lives(self):
        return self.lives
    def add_life(self):
        self.lives += 1
    def hit(self):
        self.lives -= 1
    

class Badguy:

    def __init__(self):
        side = random.randint(0, 3)
        if (side == 0):
            x = random.randint(0, 450)
            y = -150
            self.vX = 0
            self.vY = random.randint(3, 7)
        elif (side == 1):
            x = random.randint(0, 450)
            y = 650
            self.vX = 0
            self.vY = -random.randint(3, 7)
        elif (side == 2):
            x = -150
            y = random.randint(0, 450)
            self.vX = random.randint(3, 7)
            self.vY = 0
        elif (side == 3):
            x = 650
            y = random.randint(0, 450)
            self.vX = -random.randint(3, 7)
            self.vY = 0
        self.hitbox = pygame.Rect(x, y, 50, 50)
        

    def draw(self, s):
        if (self.vY == 0):
            pygame.draw.rect(s, (255, 200, 200), (0, self.hitbox.y, 500, 50))
        if (self.vX == 0):
            pygame.draw.rect(s, (255, 200, 200), (self.hitbox.x, 0, 50, 500))
        pygame.draw.rect(s, (255, 0, 0), self.hitbox)
        

    def get_hitbox(self):
        return self.hitbox
    def update(self):
        self.hitbox.x += self.vX
        self.hitbox.y += self.vY
        
    def is_dead(self):
        if self.hitbox.x < -200:
            return True
        if self.hitbox.x > 700:
            return True
        if self.hitbox.y < -200:
            return True
        if self.hitbox.y > 700:
            return True
        return False

class PowerUp:

    def __init__(self):
        self.hitbox = pygame.Rect(random.randint(0, 475), random.randint(0,475), 25, 25)
        self.type = random.randint(1,4)
        if (self.type == 1):
            self.color = (0, 255, 0)
        elif (self.type==2):
            self.color = (0, 0, 255)
        elif (self.type==3):
            self.color = (0, 0, 150)
        elif (self.type==4):
            self.color = (0, 255, 255)

    def get_hitbox(self):
        return self.hitbox

    def draw(self, s):
        pygame.draw.rect(s, self.color, self.hitbox)

    def get_type(self):
        return self.type

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
        badguys.append(Badguy())
        interval = 0
        next_spawn = 5 - math.sqrt(total_time)
        if (next_spawn < .5):
            next_spawn = .5
    if (power_spawn > 2):
        power_spawn -= 2
        power_list.append(PowerUp())
    

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

    player = Player()
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
