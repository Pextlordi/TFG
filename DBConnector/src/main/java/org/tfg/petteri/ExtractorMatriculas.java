package org.tfg.petteri;

import java.sql.*;

public class ExtractorMatriculas {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/SistemaMat";
        String user = "root";
        String password = "1234";
        try {
            Connection con = DriverManager.getConnection(url, user, password);
            Statement sentencia = con.createStatement();
            ResultSet resultado = sentencia.executeQuery("select * from Matricula where DNI_Usuario_Resp = (SELECT DNI from Usuario where Fecha_Comienzo < CURRENT_DATE and Fecha_Final > CURRENT_DATE)");
            while (resultado.next()) {
                int matricula = resultado.getInt("Numero_Mat");
                System.out.print(matricula);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
