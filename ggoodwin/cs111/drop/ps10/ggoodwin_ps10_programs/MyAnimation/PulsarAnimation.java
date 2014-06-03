import java.awt.*; // 

class PulsarAnimation extends Animation 
{ 
        public PulsarAnimation()
        {
                this.addSprite(new Pulsar(200,50,20,40,1,Color.red));
                this.addSprite(new Pulsar(300,200,0,200,5,Color.blue));
                this.setNumberFrames(Animation.NO_MAX_FRAMES);
                // this.setNumberFrames(100);
                // this.setNumberRepeats(2);
                // this.setNumberRepeats(Animation.FOREVER);
        }
}
