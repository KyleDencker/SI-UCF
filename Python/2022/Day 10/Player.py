import pygame
import math

class Player:

    def __init__(self):
        self.hitbox = pygame.Rect(225, 225, 300, 300)
        self.speed = 5
        self.lives = 361
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
