package MyListener;

import Domain.DetailBill;
import net.sf.json.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class CreateDetailBillListener implements ActionListener {

    private JFrame jFrame;
    private JButton queryButton;
    private JTextField roomTextField;
    private DetailBill detailBill;
    private HttpRequestModel queryHttpRequestModel;

    public CreateDetailBillListener(DetailBill detailBill,JFrame jFrame,JTextField roomTextField,JButton queryButton){
        this.detailBill=detailBill;
        this.jFrame=jFrame;
        this.queryButton=queryButton;
        this.roomTextField=roomTextField;
        queryHttpRequestModel=new HttpRequestModel("/detail/create","POST");

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource()==queryButton){
                int roomId= Integer.parseInt(roomTextField.getText());
                System.out.println("查询房间号"+roomId);
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("roomId",roomId);
                jsonObject.put("msgType",3);
                jsonObject=queryHttpRequestModel.send(jsonObject,"?roomId="+roomId);
            }
        }catch (NullPointerException ignored){
            JOptionPane.showMessageDialog(jFrame, "输入有误", "警告", JOptionPane.ERROR_MESSAGE);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
