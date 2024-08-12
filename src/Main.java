import java.util.Random;

public class Main {
    public static int bossHealth = 600;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {280, 270, 250, 250, 500, 150, 200, 280};
    //1.Воин 2.Маг 3.Кинетик 4.Медик 5.Голем. 6.Везунчик 7.Ведьмак 8.Тор

    public static int[] heroesDamage = {40, 40, 35, 16};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Stone"};
    public static int roundNumber;
    public static String[] heroesName = {"Warrior", "Mage", "Kineticist", "Medic", "Golem", "Lucky", "Witcher", "Thor"};

    public static void main(String[] args) {
        showStatistics();
        while (!isGameOver()) {
            playRound();
        }
    }

    public static void resurrection() { //Воскрешение Ведьмаком
        if (heroesHealth[6] > 0) {
            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i] == 0) {
                    heroesHealth[i] += heroesHealth[6];
                    System.out.println(heroesName[i] + " was resurrected");
                    heroesHealth[6] = 0;
                    break;
                }
            }
        }
    }

    public static void healing() { //Лечение
        if (heroesHealth[3] > 0) {
            for (int i = 0; i < heroesHealth.length; i++) {
                if (i != 3 && heroesHealth[i] < 100) {
                    heroesHealth[i] += 30;
                    System.out.println(heroesName[i] + " was cured");
                    break;
                }
            }
        }
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        bossAttacks();
        heroesAttack();
        healing();
        resurrection();
        showStatistics();
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0,1,2,3
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void heroesAttack() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; //2,3,4,5,6,7,8,9,10
                    damage = heroesDamage[i] * coeff;
                    System.out.println("Critical damage: " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }
            }
        }
    }

    public static boolean bossAttacks() {
        Random random = new Random();
        int golemDamage = (int) (bossDamage * 0.2);
        int newBossDamage = (int) (bossDamage * 0.8);
        boolean thorAction = random.nextBoolean();
        if (thorAction) {
            for (int i = 0; i < heroesHealth.length; i++) {

                if (heroesHealth[i] > 0) {
                    if (heroesHealth[i] - bossDamage < 0) {
                        heroesHealth[i] = 0;
                    } else {
                        if (heroesHealth[i] != heroesHealth[4] && heroesHealth[4] > 0) {
                            heroesHealth[i] = heroesHealth[i] - newBossDamage;
                            heroesHealth[4] = heroesHealth[4] - golemDamage;
                        } else {
                            heroesHealth[i] = heroesHealth[i] - bossDamage;
                        }
                    }
                    boolean luck = random.nextBoolean();
                    if (heroesHealth[i] == heroesHealth[5] && luck && heroesHealth[5] < 150) {
                        heroesHealth[5] += 50;
                        System.out.println("The lucky guy was able to dodge");
                    }
                }
            }
        } else {
            System.out.println("Boss stunned");
        }
        return false;
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void showStatistics() {

        System.out.println("ROUND " + roundNumber + " ---------------");
        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage + " defence: " +
                (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesAttackType.length; i++) {
            System.out.println(heroesAttackType[i] + " damage: " + heroesDamage[i]);
        }
        System.out.println();
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesName[i] + " health: " + heroesHealth[i]);
        }
    }
}