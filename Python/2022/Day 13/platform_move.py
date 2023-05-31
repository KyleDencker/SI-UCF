
import pygame


class platform_move:
    def __init__(self, x, y, w, h, offset_x, offset_y):
        self.hitbox = pygame.Rect(x, y, w, h)
        self.color = (0, 255, 0)
        self.start = (x, y)
        self.max_offset_x = offset_x
        self.max_offset_y = offset_y
        self.speed_x = 20
        self.speed_y = 20

        self.pos = [x, y]

    def draw(self, s):
        pygame.draw.rect(s, self.color, self.hitbox)

    def getHitbox(self):
        return self.hitbox

    def update(self, delta_time):
        self.pos[0] += self.speed_x * delta_time
        self.hitbox.x = self.pos[0]

       # print(self.hitbox.x, self.speed, delta_time, self.speed * delta_time)

        if (self.start[0] + self.max_offset_x < self.hitbox.x):
            self.hitbox.x = self.start[0] + self.max_offset_x
            self.speed_x = -self.speed_x
            self.pos[0] = self.hitbox.x 

        if (self.start[0] > self.hitbox.x):
            self.hitbox.x = self.start[0]
            self.speed_x = -self.speed_x
            self.pos[0] = self.hitbox.x 

        self.pos[1] += self.speed_y * delta_time
        self.hitbox.y = self.pos[1]

       # print(self.hitbox.x, self.speed, delta_time, self.speed * delta_time)

        if (self.start[1] + self.max_offset_y < self.hitbox.y):
            self.hitbox.y = self.start[1] + self.max_offset_y
            self.speed_y = -self.speed_y
            self.pos[1] = self.hitbox.y
        if (self.start[1] > self.hitbox.y):
            self.hitbox.y = self.start[1]
            self.speed_y = -self.speed_y
            self.pos[1] = self.hitbox.y
            
       # print(self.hitbox.x)
