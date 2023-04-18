package proyecto007.pares;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Ventana extends JFrame {
    JPanel Panel, PanelIni;
    JButton[] Cartas = new JButton[20];
    ImageIcon imagen = new ImageIcon();
    boolean ValCarta1 = false, val = false;
    boolean ValCarta2 = false;
    boolean reini = false;
    JComboBox dimension = new JComboBox();
    int opc, limite, intentos = 0, pares;
    ImageIcon Img, Img2;
    ImageIcon[] iconos = new ImageIcon[20];
    JButton[] pbtn = new JButton[2];
    String npares, si;

    public Ventana() {
        setSize(550, 700);
        setLocationRelativeTo(null);
        setTitle("JUEGO DE PARES");
        setResizable(false);
        IniciarComponentes();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true); //Hace visible la pantalla
    }

    private void IniciarComponentes() {
        IniciarPaneles();
        empezar();
    }

    private void IniciarPaneles() {
        Panel = new JPanel(new GridLayout(4, 4, 2, 2));
        Panel.setBackground(Color.gray);
    }

    public void empezar() {
        do {
            npares = JOptionPane.showInputDialog("Ingresa el numero de pares");
            try {
                pares = Integer.parseInt(npares);
                if (!(pares < 2 || pares > 10)) {
                    limite = (pares * 2);
                    val = true;
                } else {
                    JOptionPane.showMessageDialog(Panel, "Ingrese solo numeros entre 2 y 10", "Error", JOptionPane.ERROR_MESSAGE);
                    val = false;
                }
            } catch (NumberFormatException e) {
                if(npares==null)System.exit(0);
                JOptionPane.showMessageDialog(Panel, "Ingrese solo numeros enteros", "Error", JOptionPane.ERROR_MESSAGE);
                val = false;
            }
        } while (val == false);
        if (reini) {
            for (int x = 0; x < 20; x++) {
                Panel.remove(Cartas[x]);
            }
            Panel.revalidate();
            Panel.repaint();
            reiniciar();
        }
        IniciarBtn();
        Ordenar();
        AccionCarta();
        OyenteRaton();
    }

    private void IniciarBtn() {
        for (int x = 0; x < 20; x++) {
            Cartas[x] = new JButton(icono("Carta.jpg"));
        }
        this.getContentPane().add(Panel);
        bo();
    }

    public void bo() {
        for (int x = 0; x < limite; x++) {
            Panel.add(Cartas[x]);
        }
        for (int x = limite; x < 20; x++) {
            Cartas[x].setEnabled(false);
        }
    }

    //Métodos auxiliares
    public Icon icono(String carta) {
        Icon iconos;
        imagen = new ImageIcon(carta);
        iconos = new ImageIcon(imagen.getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH));
        return iconos;
    }

    private void Ordenar() {
        Panel.revalidate();
        Panel.repaint();
        int[] posiciones = new int[20];
        int cont = 0;
        int pares = limite / 2;
        while (cont < limite) {
            Random r = new Random();
            int n = r.nextInt(pares) + 1;
            int numRepe = 0;
            for (int i = 0; i < limite; i++) {
                if (posiciones[i] == n) {
                    numRepe++;
                }
            }
            if (numRepe < 2) {
                posiciones[cont] = n;
                cont++;
            }
        }
        //Les seteo una descripcion para compararlas despues
        for (int x = 0; x < 20; x++) {
            iconos[x] = (ImageIcon) icono((posiciones[x] + ".jpg"));
            iconos[x].setDescription("" + posiciones[x]);
            System.out.println(posiciones[x]);
        }
        

        //Les seteo su imagen
        for (int x = 0; x < 20; x++) {
            Cartas[x].setDisabledIcon(iconos[x]);
        }
        reini = true;
    }

    private void HabilitarBtn(JButton btn) {
        if (!ValCarta1) {
            btn.setEnabled(false);
            Img = (ImageIcon) btn.getDisabledIcon();
            pbtn[0] = btn;
            ValCarta1 = true;
            ValCarta2 = false;
        } else {
            btn.setEnabled(false);
            Img2 = (ImageIcon) btn.getDisabledIcon();
            pbtn[1] = btn;
            ValCarta2 = true;
            intentos++;
            ganar();
        }
    }

    private void comparar() {
        if (ValCarta1 && ValCarta2) {
            if (Img.getDescription().compareTo(Img2.getDescription()) != 0) {
                System.out.println(Integer.parseInt(Img.getDescription()));
                pbtn[0].setEnabled(true);
                pbtn[1].setEnabled(true);
            }
            ValCarta1 = false;
        }
    }

    private void reiniciar() {
        Ordenar();
        for (int x = 0; x < 20; x++) {
            Cartas[x].setEnabled(true);
        }
        ValCarta2 = false;
        ValCarta1 = false;
        intentos = 0;
    }

    private void ganar() {
        if (!Cartas[0].isEnabled() && !Cartas[1].isEnabled() && !Cartas[2].isEnabled() && !Cartas[3].isEnabled() && !Cartas[4].isEnabled()
                && !Cartas[5].isEnabled() && !Cartas[6].isEnabled() && !Cartas[7].isEnabled() && !Cartas[8].isEnabled()
                && !Cartas[9].isEnabled() && !Cartas[10].isEnabled() && !Cartas[11].isEnabled() && !Cartas[12].isEnabled()
                && !Cartas[13].isEnabled() && !Cartas[14].isEnabled() && !Cartas[15].isEnabled() && !Cartas[16].isEnabled()
                && !Cartas[17].isEnabled() && !Cartas[18].isEnabled() && !Cartas[19].isEnabled()) {
            JOptionPane.showMessageDialog(this, "El numero de intentos fue: " + intentos, "GANASTE", JOptionPane.INFORMATION_MESSAGE);
            for (int x = 0; x < 20; x++) {
                Panel.remove(Cartas[x]);
            }
            Panel.revalidate();
            Panel.repaint();
            reiniciar();
            empezar();
        }
    }

    //Acciones
    public void AccionCarta() {
        ActionListener BtnCarta1 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                HabilitarBtn(Cartas[0]);
            }
        };
        Cartas[0].addActionListener(BtnCarta1);

        ActionListener BtnCarta2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                HabilitarBtn(Cartas[1]);

            }
        };
        Cartas[1].addActionListener(BtnCarta2);

        ActionListener BtnCarta3 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                HabilitarBtn(Cartas[2]);

            }
        };
        Cartas[2].addActionListener(BtnCarta3);

        ActionListener BtnCarta4 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                HabilitarBtn(Cartas[3]);

            }
        };
        Cartas[3].addActionListener(BtnCarta4);

        ActionListener BtnCarta5 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                HabilitarBtn(Cartas[4]);

            }
        };
        Cartas[4].addActionListener(BtnCarta5);

        ActionListener BtnCarta6 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                HabilitarBtn(Cartas[5]);
            }
        };
        Cartas[5].addActionListener(BtnCarta6);

        ActionListener BtnCarta7 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                HabilitarBtn(Cartas[6]);
            }
        };
        Cartas[6].addActionListener(BtnCarta7);

        ActionListener BtnCarta8 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                HabilitarBtn(Cartas[7]);
            }
        };
        Cartas[7].addActionListener(BtnCarta8);

        ActionListener BtnCarta9 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                HabilitarBtn(Cartas[8]);
            }
        };
        Cartas[8].addActionListener(BtnCarta9);

        ActionListener BtnCarta10 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                HabilitarBtn(Cartas[9]);
            }
        };
        Cartas[9].addActionListener(BtnCarta10);

        ActionListener BtnCarta11 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                HabilitarBtn(Cartas[10]);
            }
        };
        Cartas[10].addActionListener(BtnCarta11);

        ActionListener BtnCarta12 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                HabilitarBtn(Cartas[11]);
            }
        };
        Cartas[11].addActionListener(BtnCarta12);

        ActionListener BtnCarta13 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                HabilitarBtn(Cartas[12]);
            }
        };
        Cartas[12].addActionListener(BtnCarta13);

        ActionListener BtnCarta14 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                HabilitarBtn(Cartas[13]);
            }
        };
        Cartas[13].addActionListener(BtnCarta14);

        ActionListener BtnCarta15 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                HabilitarBtn(Cartas[14]);
            }
        };
        Cartas[14].addActionListener(BtnCarta15);

        ActionListener BtnCarta16 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                HabilitarBtn(Cartas[15]);
            }
        };
        Cartas[15].addActionListener(BtnCarta16);

        ActionListener BtnCarta17 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                HabilitarBtn(Cartas[16]);
            }
        };
        Cartas[16].addActionListener(BtnCarta17);

        ActionListener BtnCarta18 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                HabilitarBtn(Cartas[17]);
            }
        };
        Cartas[17].addActionListener(BtnCarta18);

        ActionListener BtnCarta19 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                HabilitarBtn(Cartas[18]);
            }
        };
        Cartas[18].addActionListener(BtnCarta19);

        ActionListener BtnCarta20 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                HabilitarBtn(Cartas[19]);
            }
        };
        Cartas[19].addActionListener(BtnCarta20);
    }

    //Oyestes
    private void OyenteRaton() {
        MouseListener oyente = new MouseListener() {
            @Override
            public void mouseExited(MouseEvent me) {
                comparar();
            }
            public void mouseClicked(MouseEvent me) {
            }
            public void mousePressed(MouseEvent me) {
            }
            public void mouseReleased(MouseEvent me) {
            }
            public void mouseEntered(MouseEvent me) {
            }
        };
        for (int x = 0; x < 20; x++) {
            Cartas[x].addMouseListener(oyente);
        }
    }
}
