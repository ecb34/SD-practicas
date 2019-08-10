using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace WindowsFormsApp1
{
    public partial class login : Form
    {
        private System.Data.SqlClient.SqlConnection conn;
        public login()
        {
            InitializeComponent();
            createDataBase();
        }

        private void createDataBase()
        {
            string path =  "C:\\Users\\Public\\database.txt";
            using (System.IO.StreamWriter file = new System.IO.StreamWriter(path))
            {
                file.WriteLine("6C4bLYkmohFvPHiR9/3YaEVl6HU= E59pyTwEJJao6VjsWTBmLGzMr78=");//edu 1234
                file.WriteLine("0Ix96dAlnP5LMz/OMHij2FA/jFc= E59pyTwEJJao6VjsWTBmLGzMr78="); //profesor 1234
                
            }
        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            try
            {
                SHA1 sha = new SHA1CryptoServiceProvider();
                byte[] inputBytes = (new UnicodeEncoding()).GetBytes(textBox1.Text);
                byte[] hash = sha.ComputeHash(inputBytes);
                string usuarioCodificado = Convert.ToBase64String(hash);

                sha = new SHA1CryptoServiceProvider();
                inputBytes = (new UnicodeEncoding()).GetBytes(textBox2.Text);
                hash = sha.ComputeHash(inputBytes);
                string passwordCodificado = Convert.ToBase64String(hash);
                string path =  "C:\\Users\\Public\\database.txt";
                string[] linesDB = System.IO.File.ReadAllLines(path);
                bool existeUsuario = false;
                foreach (string line in linesDB)
                {
                    string[] usuario = line.Split(' ');//[0] = nombre, [1] password
                    if (usuario[0].Equals(usuarioCodificado))
                    {
                        existeUsuario = true;
                        if (usuario[1].Equals(passwordCodificado))
                        {
                            this.Hide();
                            consulta c = new consulta(textBox1.Text);
                            c.ShowDialog();
                            this.Close();
                            conn.Close();
                        }
                        else
                        {
                            label4.Visible = true;
                        }
                    }
                }
                if (!existeUsuario)
                {
                    label3.Visible = true;
                }
            }
            catch(Exception ex)
            {

            }
           
        }
    }
}
