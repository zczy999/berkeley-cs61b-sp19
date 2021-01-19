public class Body {
    public static final double G = 6.67*Math.pow(10, -11);

    double xxPos;
    double yyPos;
    double xxVel;
    double yyVel;
    double mass;
    String imgFileName;
    public Body(double xP, double yP, double xV,
                double yV, double m, String img){
        this.xxPos = xP;
        this.yyPos = yP;
        this.xxVel = xV;
        this.yyVel = yV;
        this.mass = m;
        this.imgFileName = img;
    }

    public Body(Body b){
        this.xxPos = b.xxPos;
        this.yyPos = b.yyPos;
        this.xxVel = b.xxVel;
        this.yyVel = b.yyVel;
        this.mass = b.mass;
        this.imgFileName = b.imgFileName;
    }

    public double calcDistance(Body b){
        double r = Math.pow((b.xxPos-this.xxPos), 2) + Math.pow((b.yyPos-this.yyPos), 2);
        return Math.sqrt(r);
    }

    public double calcForceExertedBy(Body b){
        double F = G*this.mass*b.mass/Math.pow(this.calcDistance(b), 2);
        return F;
    }

    public double calcForceExertedByX(Body b){
        double distance = calcDistance(b);
        if(distance == 0){
            return 0;
        }
        double Fx = calcForceExertedBy(b)*(b.xxPos - this.xxPos)/distance;
        return Fx;
    }

    public double calcForceExertedByY(Body b){
        double distance = calcDistance(b);
        if(distance == 0){
            return 0;
        }
        double Fy = calcForceExertedBy(b)*(b.yyPos - this.yyPos)/distance;
        return Fy;
    }

    public double calcNetForceExertedByX(Body[] bodies){
        double Fxa = 0;
        for (Body body:
             bodies) {
            Fxa += calcForceExertedByX(body);
        }
        return Fxa;
    }

    public double calcNetForceExertedByY(Body[] bodies){
        double Fya = 0;
        for (Body b:
             bodies) {
            Fya += calcForceExertedByY(b);
        }
        return Fya;
    }
}