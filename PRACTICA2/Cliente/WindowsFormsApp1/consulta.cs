using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Security.Cryptography;
using System.IO;

namespace WindowsFormsApp1
{
    public partial class consulta : Form
    {
        private string usuario;
        public consulta(string usuario)
        {
            InitializeComponent();
            this.usuario = usuario; //quien -> coger usuario
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }   
        //añadir ip
        private void button1_Click(object sender, EventArgs e)
        { 
            if (richTextBox1.Lines.Count()== 0){
                richTextBox1.Text = "";
            }
            int lineas = richTextBox1.Lines.Count();
            if (!richTextBox1.Text.Contains(textBoxAñadir.Text))
            {
                lineas++;
                try
                {
                    Estacion.Estacion estacion = new Estacion.Estacion
                    {
                        Url = "http://" + textBoxAñadir.Text + "/Estacion/services/Estacion?wsdl"
                    };
                    int id;
                    id = Int32.Parse(DecodeAndDecrypt(estacion.getId()));
                    richTextBox1.Text += "Estacion" + id + " ->" + textBoxAñadir.Text + "\n"; //modificar si se puede poner tener una id propia
                    comboBoxEstacion.Items.Add("Estacion" + id);
                }
                catch(Exception ex)
                {
                    textBoxAñadir.Text = "La ip introducida es erronea";
                }
            }
            else
            {
                textBoxAñadir.Text = "La ip introducida ya está";
            }
        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void label2_Click(object sender, EventArgs e)
        {

        }


        //escribe en log de la estacion
        private string escribirLog(string operacion)
        {
            var host = Dns.GetHostEntry(Dns.GetHostName());
            string ip = "no conectado";
            foreach (var ips in host.AddressList)
            {
                if (ips.AddressFamily == System.Net.Sockets.AddressFamily.InterNetwork)
                {//donde -> coger mi ip
                    ip = ips.ToString();//coge solo la primera ip, modificar si no coge la 192.168.56...
                    //break;
                }
            }
            string tiempoUTC = DateTime.UtcNow.ToString();//cuando -> coger mi hora
            string result = usuario + " | " + ip + " | " + tiempoUTC + " | " + operacion;
            return result;
        }
        //consulta
        private void buttonConsultar_Click(object sender, EventArgs e)
        {
            if(richTextBox1.Text != "" && comboBoxEstacion.Text.Contains("Estacion"))
            {
                try
                {
                    Estacion.Estacion estacion = obtenerEstacion();
                    int variable;
                    string operacion = ""; //qué -> coger estacion a la que fue y su operacion hecha
                    Int32.TryParse(DecodeAndDecrypt(estacion.getId()), out int id);
                    if (comboBoxVariable.Text.Contains("Humedad"))
                    {
                        operacion = "getHum";
                        variable = Int32.Parse(DecodeAndDecrypt(estacion.getHum()));
                        richTextBox2.Text += "La humedad de la estacion " + id + " es de: " + variable.ToString() + "\n";
                    }
                    if (comboBoxVariable.Text.Contains("Temperatura"))
                    {
                        operacion = "getTemp";
                        variable = Int32.Parse(DecodeAndDecrypt(estacion.getTemp()));
                        richTextBox2.Text += "La temperatura de la estacion " + id + " es de: " + variable.ToString() + "\n";
                    }
                    if (comboBoxVariable.Text.Contains("Luminosidad"))
                    {
                        operacion = "getLum";
                        variable = Int32.Parse(DecodeAndDecrypt(estacion.getLum()));
                        richTextBox2.Text += "La luminosidad de la estacion " + id + " es de: " + variable.ToString() + "\n";
                    }
                    if (comboBoxVariable.Text.Contains("Pantalla"))
                    {
                        operacion = "getPantalla";
                        string s = DecodeAndDecrypt(estacion.getLCD());
                        richTextBox2.Text += "La pantalla de la estacion " + id + " tiene el valor " + s + "\n";
                    }

                    operacion += " estacion" + id;
                    string textoLog = escribirLog(operacion);
                    estacion.setLog(textoLog);
                    string path = "C:\\Users\\Public\\log.txt";
                    using (System.IO.StreamWriter file = new System.IO.StreamWriter(path, true))
                    {
                        file.WriteLine(textoLog);
                    }
                }catch(Exception ex)
                {
                    richTextBox2.Text += "ESTACIÓN NO DISPONIBLE \n";
                    comboBoxEstacion.Items.Remove(comboBoxEstacion.Text);
                }
            }
            else
            {
                richTextBox2.Text += "Hay campos sin rellenar \n";
            }
        }

        private Estacion.Estacion obtenerEstacion()
        {
            String[] aux; // string para los splits
            aux = comboBoxEstacion.Text.Split('n'); // para busqueda de id
            int id = Int32.Parse(aux[1]); //cojo la id de la estacion
            String ip = null;
            String[] lines; // para busqueda en el while
            lines = richTextBox1.Text.Split('\n');
            foreach(string line in lines)
            {
                if (line != "")
                {
                    Console.WriteLine(line);
                    aux = line.Split('n'); // divido entre estacio y lo demas
                    aux = aux[1].Split('-'); // dividido id-> ip:puerto
                    Console.WriteLine(aux[0]);
                    if (Int32.Parse(aux[0]) == id)//comparo la encontrada con la mia
                    {
                        aux = line.Split('>'); // divido entre estacionid- y ip:puerto
                        ip = aux[1]; //cojo ip:puerto
                        Console.WriteLine(ip);
                    }
                }
            }
            Estacion.Estacion estacion = new Estacion.Estacion
            {
                Url = "http://" + ip + "/Estacion/services/Estacion?wsdl"
            };

            return estacion;
        }
        //establecer valor
        private void buttonDisplay_Click(object sender, EventArgs e)
        {
            if (richTextBox1.Text != "" && comboBoxEstacion.Text.Contains("Estacion"))
            {
                try
                {
                    Estacion.Estacion estacion = obtenerEstacion();
                    string variable;
                    string operacion = "";
                    Int32.TryParse(DecodeAndDecrypt(estacion.getId()), out int id);
                    try
                    {
                        if (comboBoxEstablecer.Text != "" && comboBoxEstacion.Text.Contains("Estacion"))
                        {
                            if (Int32.TryParse(textBoxDisplay.Text, out int x))
                            {
                                if (comboBoxEstablecer.Text.Contains("Humedad"))
                                {
                                    operacion = "setHum";
                                    variable = textBoxDisplay.Text;
                                    estacion.setHum(EncryptAndEncode(variable));
                                }
                                if (comboBoxEstablecer.Text.Contains("Temperatura"))
                                {
                                    operacion = "setTemp";
                                    variable = textBoxDisplay.Text;
                                    estacion.setTemp(EncryptAndEncode(variable));
                                }
                                if (comboBoxEstablecer.Text.Contains("Luminosidad"))
                                {
                                    operacion = "setLum";
                                    variable = textBoxDisplay.Text;
                                    estacion.setLum(EncryptAndEncode(variable));
                                }
                                labelError2.Visible = false;
                                label3.Visible = true;
                            }
                            else
                            {
                                labelError2.Visible = true;
                                label3.Visible = false;
                            }
                            if (comboBoxEstablecer.Text.Contains("Pantalla"))
                            {
                                operacion = "setPantalla";
                                estacion.setLCD(EncryptAndEncode(textBoxDisplay.Text));
                                labelError2.Visible = false;
                                label3.Visible = true;
                            }
                            if(operacion.Contains("set"))
                            {
                                operacion += " estacion" + id;
                                string logLine = escribirLog(operacion);
                                estacion.setLog(logLine);
                                string path = "C:\\Users\\Public\\log.txt"; ;
                                using (System.IO.StreamWriter file = new System.IO.StreamWriter(path, true))
                                {
                                    file.WriteLine(logLine);
                                }
                            }    
                        }
                    }
                    catch (Exception ex)
                    {
                        labelError2.Visible = true;
                        label3.Visible = false;
                    }
                }catch(Exception ex)
                {
                    textBoxDisplay.Text = "ESTACIÓN NO DISPONIBLE";
                    comboBoxEstacion.Items.Remove(comboBoxEstacion.Text);
                }
            }
        }
        private static string IV = "IV_VALUE_16_BYTE";
        private static string PASSWORD = "PASSWORD_VALUE";
        private static string SALT = "SALT_VALUE";

        public static string EncryptAndEncode(string raw)
        {
            using (var csp = new AesCryptoServiceProvider())
            {
                ICryptoTransform e = GetCryptoTransform(csp, true);
                byte[] inputBuffer = Encoding.UTF8.GetBytes(raw);
                byte[] output = e.TransformFinalBlock(inputBuffer, 0, inputBuffer.Length);
                string encrypted = Convert.ToBase64String(output);
                return encrypted;
            }
        }

        public static string DecodeAndDecrypt(string encrypted)
        {
            using (var csp = new AesCryptoServiceProvider())
            {
                var d = GetCryptoTransform(csp, false);
                byte[] output = Convert.FromBase64String(encrypted);
                byte[] decryptedOutput = d.TransformFinalBlock(output, 0, output.Length);
                string decypted = Encoding.UTF8.GetString(decryptedOutput);
                return decypted;
            }
        }

        private static ICryptoTransform GetCryptoTransform(AesCryptoServiceProvider csp, bool encrypting)
        {
            csp.Mode = CipherMode.CBC;
            csp.Padding = PaddingMode.PKCS7;
            var spec = new Rfc2898DeriveBytes(Encoding.UTF8.GetBytes(PASSWORD), Encoding.UTF8.GetBytes(SALT), 65536);
            byte[] key = spec.GetBytes(16);


            csp.IV = Encoding.UTF8.GetBytes(IV);
            csp.Key = key;
            if (encrypting)
            {
                return csp.CreateEncryptor();
            }
            return csp.CreateDecryptor();
        }
    }
   
}
