import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Main {
    static int questionsPerOptionValue = 3;
    static int optionsIntoValue = 2;
    static int optionsOutValue = 2;
    static String fileNameValue = "test";
    static FrameClass third;
    static boolean doesOpen = false;
    static String fileType = ".doc";
    static ArrayList<Variant> vars;
    static final String filler = "filler";
    public static void main(String[] args) {
        FrameClass first = new FrameClass("Test Creator v.3.1");
        firstGir(first);
    }


    private static ArrayList<Variant> createVariants(ArrayList<Question> vars) {
        ArrayList<Variant> result = new ArrayList<>();
        for (int i = 0; i < optionsOutValue; i++) {
            ArrayList<Question> quests = new ArrayList<Question>();
            for (int j = 0; j < questionsPerOptionValue; j++) {
                int x;
                x = (int) (Math.random() * (optionsIntoValue));
                System.out.println(x);
                if ((x * i) + j == vars.size()) x--;
                vars.get((x * i) + j).shuffleList();//works
                if (!quests.contains(vars.get((x * i) + j)))
                    quests.add(vars.get((x * questionsPerOptionValue) + j));
                else {
                    j--;
                }
            }
            result.add(new Variant(quests));
        }
        return result;
    }
    private static void firstGir(FrameClass first) {
        JTextField questionPerOption = first.createField(
                new Rectangle(0, 0, 540, 100));
        JLabel questionPerOptionLabel = first.createLabel(
                "Количество вопросов на вариант",
                new Rectangle(540, 0, 540, 100),
                Color.BLACK
        );


        JTextField optionsInto = first.createField(
                new Rectangle(0, 100, 540, 100)
        );
        JLabel optionsIntoLabel = first.createLabel(
                "Количество вводных вариантов",
                new Rectangle(540, 100, 540, 100),
                Color.black
        );


        JTextField optionsOut = first.createField(
                new Rectangle(0, 200, 540, 100)
        );
        JLabel optionsOutLabel = first.createLabel(
                "Количество нужных Вам вариантов",
                new Rectangle(540, 200, 540, 100),
                Color.black
        );


        JTextField fileName = first.createField(
                new Rectangle(0, 300, 540, 100)
        );
        JLabel fileNameLabel = first.createLabel(
                "Запишите имя вашего файла",
                new Rectangle(540, 300, 540, 100),
                Color.black
        );

        JLabel errorTextLabel = first.createLabel(
                " ",
                new Rectangle(0, 400, 540, 100),
                Color.black
        );

        JButton button = first.createButton("Ввод",
                new Rectangle(0, 500, 100, 100),
                e -> {
                    questionsPerOptionValue = strtoint(questionPerOption.getText());
                    optionsIntoValue = strtoint(optionsInto.getText());
                    optionsOutValue = strtoint(optionsOut.getText());
                    fileNameValue = fileName.getText();
                    if (questionsPerOptionValue > 0 && optionsIntoValue > 0 && optionsOutValue > 0) {
                        first.frame.setVisible(false);
                        secondGir();
                    } else {
//                        l.add("Что-то пошло не так. Проверьте входные данные");
                        errorTextLabel.setText("Что-то пошло не так. Проверьте входные данные");
                    }
                }
        );

    }

    private static void secondGir() {
        FrameClass second = new FrameClass("Шаг 2: ввод вопросов");
        JTextField question = second.createField(
                new Rectangle(0, 0, 540, 100)
        );
        JLabel questionLabel = second.createLabel(
                "Вопрос",
                new Rectangle(540, 0, 540, 100),
                Color.black
        );
        //заметь - диму и тут он выделил ошибкой, а пиздец - нет
        int[] position = new int[]{0};
        final ArrayList<String>[] intermediate = new ArrayList[]{new ArrayList<String>()};
        intermediate[0].add(filler);
        JTextField answerI = second.createField(
                new Rectangle(150, 100, 400, 100)
        );
        answerI.addCaretListener(e -> {
            if (!answerI.getText().equals("")) {
                intermediate[0].set(position[0], answerI.getText());
            }
        });

        JLabel answerILabel = second.createLabel(
                "Вариант ответа " + (position[0] + 1),
                new Rectangle(550, 100, 600, 100),
                Color.black
        );


        JLabel toPreviousTextLabel = second.createLabel(
                "Предыдущий вариант ответа",
                new Rectangle(0, 210, 540, 100),
                Color.black
        );


        JLabel toNextTextLabel = second.createLabel(
                "Следующий вариант ответа",
                new Rectangle(700, 210, 540, 100),
                Color.black
        );


        JTextField trueAnswer = second.createField(
                new Rectangle(0, 310, 540, 100)
        );

        JLabel trueAnswerLabel = second.createLabel(
                "Введите правильный ответ",
                new Rectangle(540, 310, 540, 100),
                Color.black
        );


        int[] t = new int[]{questionsPerOptionValue * optionsIntoValue};

        java.awt.List questionsLeft = second.createAwtList(
                new Rectangle(0, 410, 1080, 100),
                "Введите вопросы. Осталось: " + t[0]
        );


        JButton previousAnswerOption = new JButton();
        previousAnswerOption.setBounds(0, 100, 100, 100);
        second.frame.add(previousAnswerOption);

        previousAnswerOption.addActionListener((ActionEvent e) -> {
            if (position[0] != 0) {
                intermediate[0].set(position[0], answerI.getText());
                answerI.setText("");
                position[0]--;
                answerILabel.setText("Вариант ответа " + (position[0] + 1));

                if (!intermediate[0].get(position[0]).equals(filler))
                    answerI.setText(intermediate[0].get(position[0]));


            }
        });


        JButton nextAnswerOption = new JButton();
        nextAnswerOption.setBounds(700, 100, 100, 100);
        second.frame.add(nextAnswerOption);

        nextAnswerOption.addActionListener((ActionEvent e) -> {
            if (position[0] != intermediate[0].size() - 1) {
                intermediate[0].set(position[0], answerI.getText());
                answerI.setText("");
                position[0]++;
                answerILabel.setText("Вариант ответа " + (position[0] + 1));
            } else {
                intermediate[0].add(filler);
                position[0]++;
                answerILabel.setText("Вариант ответа " + (position[0] + 1));
                answerI.setText("");
            }
            if (!intermediate[0].get(position[0]).equals(filler))
                answerI.setText(intermediate[0].get(position[0]));
        });
        ArrayList<Question> questions = new ArrayList<>();
        JButton addQuestion = second.createButton("",
                new Rectangle(0, 510, 100, 100),
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        questionsLeft.clear();
                        questions.add(new Question(question.getText(), intermediate[0], trueAnswer.getText()));
                        if (t[0] == 1) {
                            vars = createVariants(questions);
                            second.frame.setVisible(false);
                            thirdGir();
                        } else {

//                            intermediate.removeAll(intermediate);

                            intermediate[0] = new ArrayList<>();
                            trueAnswer.setText("");
                            question.setText("");

                            t[0]--;
                            questionsLeft.add("Введите вопросы. Осталось: " + t[0]);
//                            intermediate[0].set(position[0],answerI.getText());
//                            intermediate[0].clear();
                            intermediate[0].add(filler);
                            position[0] = 0;
                            answerILabel.setText("Вариант ответа " + (position[0] + 1));
                            answerI.setText("");
                        }
                    }
                }
        );

        JLabel addquestionLabel = second.createLabel("Добавить вопрос",
                new Rectangle(0, 610, 500, 100),
                Color.BLACK
        );


        JButton deleteLast = new JButton();
        deleteLast.setBounds(540, 510, 100, 100);
        second.frame.add(deleteLast);

        deleteLast.addActionListener(e -> {
            if (intermediate[0].size() != 2) {
                intermediate[0].remove(intermediate[0].size() - 1);
            }

            if (position[0] >= intermediate[0].size()) {
                position[0] = intermediate[0].size() - 1;
                answerILabel.setText("Вариант ответа " + (position[0] + 1));

                if (!intermediate[0].get(position[0]).equals(filler))
                    answerI.setText(intermediate[0].get(position[0]));
            }

        });
        deleteLast.setVisible(true);


        JLabel deleteLastLabel = second.createLabel("Удалить последний вариант ответа",
                new Rectangle(540, 610, 500, 100),
                Color.BLACK
        );


    }

    private static void thirdGir() {
        third = new FrameClass("Шаг 3: Скачивание");
        String[] items = new String[]{".doc", ".txt",".pdf"};
        JComboBox chooseFileTypeBox = new JComboBox(items);
        chooseFileTypeBox.setSelectedIndex(0);
        chooseFileTypeBox.addActionListener(e -> {
            fileType = (String) chooseFileTypeBox.getSelectedItem();
            System.out.println(fileType);
        });
        chooseFileTypeBox.setBounds(third.frame.getWidth() / 4, (third.frame.getHeight() / 4) + 100, 500, 100);
        third.frame.add(chooseFileTypeBox);
        chooseFileTypeBox.setVisible(true);
        JCheckBox openFile = new JCheckBox("Открыть готовый файл?", false);
        openFile.addItemListener(e -> {
            doesOpen = !doesOpen;
            System.out.println(doesOpen);
        });
        openFile.setBounds(third.frame.getWidth() / 4, (third.frame.getHeight() / 4) + 200, 500, 100);
        third.frame.add(openFile);
        openFile.setVisible(true);
        JLabel chooseFileType = third.createLabel("Выберите тип файла",
                new Rectangle(third.frame.getWidth() / 4, third.frame.getHeight() / 4, 1000, 100),
                Color.black
        );
        chooseFileType.setFont(new Font("Roman", Font.BOLD, 48));
        JButton finish = third.createButton("Finish",
                new Rectangle(third.frame.getWidth() / 4, (third.frame.getHeight() / 4) + 300, 100, 100),
                e -> {
                    Downloader downloader = new Downloader(vars, fileType, doesOpen, fileNameValue);
                    downloader.download();
                }
        );
    }

    private static int strtoint(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}

class Downloader {
    ArrayList<Variant> variants;
    String fileType;
    boolean open;
    String fileName;
    public Downloader(ArrayList<Variant> variants, String fileType, boolean open, String fileName) {
        this.variants = variants;
        this.fileType = fileType;
        this.open = open;
        this.fileName = fileName;
    }
    public void download() {
        ArrayList<String> fileText = new ArrayList<>();
        ArrayList<String> truanswers = new ArrayList<>();

        for (int i = 0; i < variants.size(); i++) {
            fileText.add("Вариант " + (i + 1));
            truanswers.add("Вариант " + (i + 1));
            for (int j = 0; j < variants.get(i).questions.size(); j++) {
                fileText.add(System.lineSeparator());
                truanswers.add(System.lineSeparator());

                fileText.add("Вопрос " + (j + 1));
                fileText.add(variants.get(i).questions.get(j).question);
                fileText.addAll(variants.get(i).questions.get(j).listOfAnswers);

                truanswers.add((j + 1) + ". " + variants.get(i).questions.get(j).answer);
            }
            fileText.add(System.lineSeparator());
            truanswers.add(System.lineSeparator());
        }

        if (fileType.equals(".pdf")) {
            write(fileText, false);
            write(truanswers, true);
        } else {
            saveLevel(fileText, false);
            saveLevel(truanswers, true);
        }

        if (open) {
            try {
                Desktop.getDesktop().open(new File("D:\\" + fileName + fileType));
                Desktop.getDesktop().open(new File("D:\\" + fileName + "(answers)" + fileType));
            } catch (IOException e) {
//                throw new RuntimeException(e);
                e.printStackTrace();
            }
        } else
            JOptionPane.showMessageDialog(Main.third.frame, new String[]{"текст теста записан в: D:\\" + fileName + fileType, "текст ответов записан в: D:\\" + fileName + "(answers)" + fileType});

        System.exit(42);

    }
    public void saveLevel(ArrayList<String> noname, boolean areanswers) {
        File Platforms = new File(("D:\\" + fileName + (areanswers ? "(answers)" : "") + fileType));//создали файл
        try {
            Platforms.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        saveStringToFile(noname, Platforms);//производим сохранение
    }
    public void appendStrToFile(File file, String str) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("D:\\" + file.getName(), true));
            out.write(str);
            out.newLine();
            out.close();
        } catch (IOException e) {
            System.out.println("exception occurred" + e);
        }
    }
    public void saveStringToFile(ArrayList<String> noname, File file) {
        for (String string : noname) {
            appendStrToFile(file, string);//для всех строчек результата производим записывание в файл
        }
    }
    public void write(ArrayList<String> noname, boolean areanswers) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("D:\\" + fileName + (areanswers ? "(answers)" : "") + ".pdf"));
            document.open();

            BaseFont bf = BaseFont.createFont("fonts\\AboriginalSerifREGULAR.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            for (int i = 0; i < noname.size(); i++) {
                Paragraph paragraph = new Paragraph(noname.get(i), new com.itextpdf.text.Font(bf, 14));
                document.add(paragraph);
            }
            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

}
