import pygame

class player:

    def __init__(self, hb):
        self.hitbox = hb
        self.velo = [0, 0]
        self.color = (255, 0, 255)
        self.canJump = True

    def draw(self, s):
        pygame.draw.rect(s, self.color, self.hitbox)

    def update(self, time):

        self.hitbox.y -= self.velo[1] * time
        self.velo[1] -= 500 * time
        #print("Change:", self.velo[1] * time, "V:", self.velo[1])

    def left(self, time):
        self.hitbox.x -= 250 * time
        if (self.hitbox.x < -self.hitbox.width):
            self.hitbox.x = 500
        
    def right(self, time):
        self.hitbox.x += 250 * time
        if (self.hitbox.x > 500):
            self.hitbox.x = -self.hitbox.width
            
    def jump(self):
        if (self.canJump):
            self.velo[1] = 200
            self.canJump = False

    def checkHit(self,box):
        if self.hitbox.colliderect(box):
            if self.velo[1] > 0:
                self.hitbox.y = box.y + box.height
            else:
                self.hitbox.y = box.y - self.hitbox.height
            self.velo[1] = 0
            self.canJump = True

    def getHitbox(self):
        return self.hitbox
