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
        if (self.hitbox.x < 0):
            self.hitbox.x = 0
        if (self.hitbox.x > 500 - self.hitbox.width):
            self.hitbox.x = 500 - self.hitbox.width
        if (self.hitbox.y < 0):
            self.hitbox.y = 0
        if (self.hitbox.y > 500 - self.hitbox.height):
            self.hitbox.y = 500 - self.hitbox.height

    def draw(self, s):
        pygame.draw.rect(s, (0, 0, 0), self.hitbox)

    def get_speed(self):
        return self.speed
    def add_speed(self):
        self.speed+=1
    def get_small(self):
        self.hitbox.width -= 5
        self.hitbox.height -= 5
        if (self.hitbox.width < 5):
            self.hitbox.width = 5
            self.hitbox.height = 5
    def reset(self):
        self.hitbox = pygame.Rect(225, 225, 50, 50)
        self.speed = 5
    def get_hitbox(self):
        return self.hitbox
    

class Badguy:

    def __init__(self):
        side = random.randint(0, 3)
        if (side == 0):
            x = random.randint(0, 450)
            y = -50
            self.vX = 0
            self.vY = random.randint(3, 7)
        elif (side == 1):
            x = random.randint(0, 450)
            y = 500
            self.vX = 0
            self.vY = -random.randint(3, 7)
        elif (side == 2):
            x = -50
            y = random.randint(0, 450)
            self.vX = random.randint(3, 7)
            self.vY = 0
        elif (side == 3):
            x = 500
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
        if self.hitbox.x < -50:
            return True
        if self.hitbox.x > 500:
            return True
        if self.hitbox.y < -50:
            return True
        if self.hitbox.y > 500:
            return True
        return False

class PowerUp:

    def __init__(self):
        self.hitbox = pygame.Rect(random.randint(0, 475), random.randint(0,475), 25, 25)

    def get_hitbox(self):
        return self.hitbox

    def draw(self, s):
        pygame.draw.rect(s, (0, 255, 0), self.hitbox)

def update(time):
    global total_time
    global interval
    global next_spawn
    global power_spawn

    
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
            player.reset()
            interval = 0
            next_spawn = 1
            total_time = 0
            badguys.clear()
            
    for p in power_list:
        if (p.get_hitbox().colliderect(player.get_hitbox())):
            choice = random.randint(1,2)
            if (choice == 1):
                player.add_speed()
            if (choice == 2):
                player.get_small()
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
    s.fill ( (255, 255, 255) )
    
    for bg in badguys:
        bg.draw(s)
    for p in power_list:
        p.draw(s)
    player.draw(s)

def main():

    global player
    global badguys
    global key_input
    global total_time
    global interval
    global next_spawn
    global power_list
    global power_spawn

    next_spawn = 1

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
                    key_input['left'] = True
                if event.key == pygame.K_RIGHT:
                    key_input['right'] = True
                if event.key == pygame.K_UP:
                    key_input['up'] = True
                if event.key == pygame.K_DOWN:
                    key_input['down'] = True
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
