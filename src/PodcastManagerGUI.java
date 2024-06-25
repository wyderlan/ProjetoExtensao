import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Scanner;

public class PodcastManagerGUI extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DefaultListModel<Episodio> episodioListModel;
    private JList<Episodio> episodioList;
    private JTextField tituloField;

    private static final String TITLE = "Gerenciador de Episódios - Canhão Podcast";
    private static final String ADD_BUTTON_LABEL = "Adicionar Episódio";
    private static final String REMOVE_BUTTON_LABEL = "Remover Episódio";
    private static final String CLEAR_BUTTON_LABEL = "Limpar";
    private static final String EXPORT_BUTTON_LABEL = "Exportar para TXT";
    private static final String IMPORT_BUTTON_LABEL = "Importar de TXT";
    private static final String SUCCESS_TITLE = "Sucesso";
    private static final String ADD_SUCCESS_MESSAGE = "Episódio adicionado com sucesso.";
    private static final String TITLE_EMPTY_ERROR = "O título do episódio não pode estar vazio.";
    private static final String REMOVE_SUCCESS_MESSAGE = "Episódio removido com sucesso.";
    private static final String SELECT_ERROR_MESSAGE = "Selecione um episódio para remover.";
    private static final String EXPORT_SUCCESS_MESSAGE = "Episódios exportados para arquivo TXT.";
    private static final String IMPORT_SUCCESS_MESSAGE = "Episódios importados do arquivo TXT.";

    public PodcastManagerGUI() {
        setTitle(TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        // Initialize components
        episodioListModel = new DefaultListModel<>();
        episodioList = new JList<>(episodioListModel);
        JScrollPane scrollPane = new JScrollPane(episodioList);
        JLabel tituloLabel = new JLabel("Título:");
        tituloField = new JTextField(30);
        JButton adicionarButton = new JButton(ADD_BUTTON_LABEL);
        JButton removerButton = new JButton(REMOVE_BUTTON_LABEL);
        JButton limparButton = new JButton(CLEAR_BUTTON_LABEL);
        JButton exportarButton = new JButton(EXPORT_BUTTON_LABEL);
        JButton importarButton = new JButton(IMPORT_BUTTON_LABEL);

        // Layout setup
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        formPanel.add(tituloLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(tituloField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(adicionarButton);
        buttonPanel.add(removerButton);
        buttonPanel.add(limparButton);
        buttonPanel.add(exportarButton);
        buttonPanel.add(importarButton);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(formPanel, BorderLayout.CENTER);
        inputPanel.add(buttonPanel, BorderLayout.SOUTH);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.EAST);

        getContentPane().add(mainPanel);

        // Adicionar episódios para teste inicial
        adicionarEpisodiosIniciais();

        // List selection listener
        episodioList.addListSelectionListener(e -> {
            Episodio selectedEpisodio = episodioList.getSelectedValue();
            if (selectedEpisodio != null) {
                tituloField.setText(selectedEpisodio.getTitulo());
            }
        });

        // Adicionar botões de ação
        adicionarButton.addActionListener(e -> adicionarEpisodio());
        removerButton.addActionListener(e -> removerEpisodio());
        limparButton.addActionListener(e -> limparCampos());
        exportarButton.addActionListener(e -> exportarEpisodiosParaTXT());
        importarButton.addActionListener(e -> importarEpisodiosDeTXT());

        // Mostrar a GUI
        setVisible(true);
    }

    private void adicionarEpisodiosIniciais() {
        adicionarEpisodio("EP #2 - CANHÃO PODCAST - COM JOÃO CATUNDA");
        adicionarEpisodio("EP #3 - CANHÃO PODCAST - COM LOBÃO");
        adicionarEpisodio("EP #4 - CANHÃO PODCAST - COM SIDERLANE MENDONÇA");
        adicionarEpisodio("EP #5 - CANHÃO PODCAST - COM DAVI DAVINO FILHO");
        adicionarEpisodio("EP #6 - CANHÃO PODCAST - COM JUDSON CABRAL");
        adicionarEpisodio("EP #8 - CANHÃO PODCAST - COM FRANCISCO SALES");
        adicionarEpisodio("EP #9 - CANHÃO PODCAST - COM RUI PALMEIRA");
        adicionarEpisodio("EP #10 - CANHÃO PODCAST - COM ALEXANDRE AYRES");
        adicionarEpisodio("EP #11 - CANHÃO PODCAST - COM CABO BEBETO");
        adicionarEpisodio("EP #12 - CANHÃO PODCAST - COM PAULÃO DO PT");
        adicionarEpisodio("EP #13 - CANHÃO PODCAST - COM MARCIUS BELTRÃO");
        adicionarEpisodio("EP #14 - CANHÃO PODCAST - COM FRANCISCO TENÓRIO");
        adicionarEpisodio("EP #15 - CANHÃO PODCAST - COM DELEGADO THIAGO PRADO");
        adicionarEpisodio("EP #16 - CANHÃO PODCAST - COM CÍCERO ALBUQUERQUE");
        adicionarEpisodio("EP #17 - CANHÃO PODCAST - COM WALTER DO VALLE");
        adicionarEpisodio("EP #18 - CANHÃO PODCAST - COM LUCIANO ALMEIDA - CANDIDATO A GOVERNADOR");
        adicionarEpisodio("EP #19 - CANHÃO PODCAST - COM RAFAEL TENÓRIO - SENADOR");
        adicionarEpisodio("EP #20 - CANHÃO PODCAST - ELEIÇÕES 2022 COM MARCELO BASTOS - ANALISTA POLÍTICO");
        // Adicione mais episódios iniciais conforme necessário
    }

    private void adicionarEpisodio() {
        String titulo = tituloField.getText().trim();
        if (!titulo.isEmpty()) {
            adicionarEpisodio(titulo);
            limparCampos();
            JOptionPane.showMessageDialog(this, ADD_SUCCESS_MESSAGE, SUCCESS_TITLE, JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, TITLE_EMPTY_ERROR, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removerEpisodio() {
        int selectedIndex = episodioList.getSelectedIndex();
        if (selectedIndex != -1) {
            episodioListModel.remove(selectedIndex);
            limparCampos();
            JOptionPane.showMessageDialog(this, REMOVE_SUCCESS_MESSAGE, SUCCESS_TITLE, JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, SELECT_ERROR_MESSAGE, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        tituloField.setText("");
    }

    private void adicionarEpisodio(String titulo) {
        episodioListModel.addElement(new Episodio(titulo));
    }

    private void exportarEpisodiosParaTXT() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Exportar para TXT");
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (PrintWriter writer = new PrintWriter(fileToSave)) {
                for (int i = 0; i < episodioListModel.size(); i++) {
                    Episodio episodio = episodioListModel.get(i);
                    writer.println(episodio.getTitulo());
                }
                JOptionPane.showMessageDialog(this, EXPORT_SUCCESS_MESSAGE, SUCCESS_TITLE, JOptionPane.INFORMATION_MESSAGE);
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar arquivo.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void importarEpisodiosDeTXT() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Importar de TXT");
        int userSelection = fileChooser.showOpenDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToOpen = fileChooser.getSelectedFile();
            try (Scanner scanner = new Scanner(fileToOpen)) {
                episodioListModel.clear();
                while (scanner.hasNextLine()) {
                    String titulo = scanner.nextLine().trim();
                    if (!titulo.isEmpty()) {
                        episodioListModel.addElement(new Episodio(titulo));
                    }
                }
                JOptionPane.showMessageDialog(this, IMPORT_SUCCESS_MESSAGE, SUCCESS_TITLE, JOptionPane.INFORMATION_MESSAGE);
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Arquivo não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PodcastManagerGUI::new);
    }
}

class Episodio {
    private String titulo;

    public Episodio(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    @Override
    public String toString() {
        return titulo;
    }
}
