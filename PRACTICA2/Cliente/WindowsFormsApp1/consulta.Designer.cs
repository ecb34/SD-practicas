namespace WindowsFormsApp1
{
    partial class consulta
    {
        /// <summary>
        /// Variable del diseñador necesaria.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Limpiar los recursos que se estén usando.
        /// </summary>
        /// <param name="disposing">true si los recursos administrados se deben desechar; false en caso contrario.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Código generado por el Diseñador de Windows Forms

        /// <summary>
        /// Método necesario para admitir el Diseñador. No se puede modificar
        /// el contenido de este método con el editor de código.
        /// </summary>
        private void InitializeComponent()
        {
            this.buttonAñadir = new System.Windows.Forms.Button();
            this.textBoxAñadir = new System.Windows.Forms.TextBox();
            this.comboBoxEstacion = new System.Windows.Forms.ComboBox();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.richTextBox1 = new System.Windows.Forms.RichTextBox();
            this.richTextBox2 = new System.Windows.Forms.RichTextBox();
            this.buttonConsultar = new System.Windows.Forms.Button();
            this.comboBoxVariable = new System.Windows.Forms.ComboBox();
            this.textBoxDisplay = new System.Windows.Forms.TextBox();
            this.buttonDisplay = new System.Windows.Forms.Button();
            this.comboBoxEstablecer = new System.Windows.Forms.ComboBox();
            this.labelError2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // buttonAñadir
            // 
            this.buttonAñadir.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.buttonAñadir.Location = new System.Drawing.Point(287, 37);
            this.buttonAñadir.Name = "buttonAñadir";
            this.buttonAñadir.Size = new System.Drawing.Size(99, 44);
            this.buttonAñadir.TabIndex = 0;
            this.buttonAñadir.Text = "añadir";
            this.buttonAñadir.UseVisualStyleBackColor = true;
            this.buttonAñadir.Click += new System.EventHandler(this.button1_Click);
            // 
            // textBoxAñadir
            // 
            this.textBoxAñadir.Location = new System.Drawing.Point(35, 49);
            this.textBoxAñadir.Name = "textBoxAñadir";
            this.textBoxAñadir.Size = new System.Drawing.Size(185, 22);
            this.textBoxAñadir.TabIndex = 1;
            this.textBoxAñadir.Text = "ip:puerto";
            // 
            // comboBoxEstacion
            // 
            this.comboBoxEstacion.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.comboBoxEstacion.FormattingEnabled = true;
            this.comboBoxEstacion.Location = new System.Drawing.Point(35, 245);
            this.comboBoxEstacion.Name = "comboBoxEstacion";
            this.comboBoxEstacion.Size = new System.Drawing.Size(185, 24);
            this.comboBoxEstacion.TabIndex = 2;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 13.8F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(35, 13);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(110, 29);
            this.label1.TabIndex = 3;
            this.label1.Text = "Conectar";
            this.label1.Click += new System.EventHandler(this.label1_Click);
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 13.8F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label2.Location = new System.Drawing.Point(35, 212);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(89, 29);
            this.label2.TabIndex = 4;
            this.label2.Text = "Operar";
            this.label2.Click += new System.EventHandler(this.label2_Click);
            // 
            // richTextBox1
            // 
            this.richTextBox1.Location = new System.Drawing.Point(35, 99);
            this.richTextBox1.Name = "richTextBox1";
            this.richTextBox1.ReadOnly = true;
            this.richTextBox1.Size = new System.Drawing.Size(365, 96);
            this.richTextBox1.TabIndex = 5;
            this.richTextBox1.Text = "";
            // 
            // richTextBox2
            // 
            this.richTextBox2.Location = new System.Drawing.Point(35, 331);
            this.richTextBox2.Name = "richTextBox2";
            this.richTextBox2.ReadOnly = true;
            this.richTextBox2.Size = new System.Drawing.Size(365, 96);
            this.richTextBox2.TabIndex = 6;
            this.richTextBox2.Text = "";
            // 
            // buttonConsultar
            // 
            this.buttonConsultar.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.buttonConsultar.Location = new System.Drawing.Point(287, 268);
            this.buttonConsultar.Name = "buttonConsultar";
            this.buttonConsultar.Size = new System.Drawing.Size(99, 48);
            this.buttonConsultar.TabIndex = 7;
            this.buttonConsultar.Text = "consultar";
            this.buttonConsultar.UseVisualStyleBackColor = true;
            this.buttonConsultar.Click += new System.EventHandler(this.buttonConsultar_Click);
            // 
            // comboBoxVariable
            // 
            this.comboBoxVariable.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.comboBoxVariable.FormattingEnabled = true;
            this.comboBoxVariable.Items.AddRange(new object[] {
            "Temperatura",
            "Luminosidad",
            "Humedad",
            "Pantalla"});
            this.comboBoxVariable.Location = new System.Drawing.Point(35, 292);
            this.comboBoxVariable.Name = "comboBoxVariable";
            this.comboBoxVariable.Size = new System.Drawing.Size(185, 24);
            this.comboBoxVariable.TabIndex = 8;
            // 
            // textBoxDisplay
            // 
            this.textBoxDisplay.Location = new System.Drawing.Point(35, 498);
            this.textBoxDisplay.Name = "textBoxDisplay";
            this.textBoxDisplay.Size = new System.Drawing.Size(185, 22);
            this.textBoxDisplay.TabIndex = 9;
            // 
            // buttonDisplay
            // 
            this.buttonDisplay.Location = new System.Drawing.Point(287, 468);
            this.buttonDisplay.Name = "buttonDisplay";
            this.buttonDisplay.Size = new System.Drawing.Size(99, 52);
            this.buttonDisplay.TabIndex = 10;
            this.buttonDisplay.Text = "establecer valor";
            this.buttonDisplay.UseVisualStyleBackColor = true;
            this.buttonDisplay.Click += new System.EventHandler(this.buttonDisplay_Click);
            // 
            // comboBoxEstablecer
            // 
            this.comboBoxEstablecer.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.comboBoxEstablecer.FormattingEnabled = true;
            this.comboBoxEstablecer.Items.AddRange(new object[] {
            "Temperatura",
            "Humedad",
            "Luminosidad",
            "Pantalla"});
            this.comboBoxEstablecer.Location = new System.Drawing.Point(35, 457);
            this.comboBoxEstablecer.Name = "comboBoxEstablecer";
            this.comboBoxEstablecer.Size = new System.Drawing.Size(185, 24);
            this.comboBoxEstablecer.TabIndex = 11;
            // 
            // labelError2
            // 
            this.labelError2.AutoSize = true;
            this.labelError2.ForeColor = System.Drawing.Color.Red;
            this.labelError2.Location = new System.Drawing.Point(35, 536);
            this.labelError2.Name = "labelError2";
            this.labelError2.Size = new System.Drawing.Size(177, 17);
            this.labelError2.TabIndex = 12;
            this.labelError2.Text = "El valor debe ser numérico";
            this.labelError2.Visible = false;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.ForeColor = System.Drawing.Color.Lime;
            this.label3.Location = new System.Drawing.Point(235, 536);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(170, 17);
            this.label3.TabIndex = 13;
            this.label3.Text = "El valor ha sido cambiado";
            this.label3.Visible = false;
            // 
            // consulta
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(436, 565);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.labelError2);
            this.Controls.Add(this.comboBoxEstablecer);
            this.Controls.Add(this.buttonDisplay);
            this.Controls.Add(this.textBoxDisplay);
            this.Controls.Add(this.comboBoxVariable);
            this.Controls.Add(this.buttonConsultar);
            this.Controls.Add(this.richTextBox2);
            this.Controls.Add(this.richTextBox1);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.comboBoxEstacion);
            this.Controls.Add(this.textBoxAñadir);
            this.Controls.Add(this.buttonAñadir);
            this.Name = "consulta";
            this.Text = "Controlador de Estaciones";
            this.Load += new System.EventHandler(this.Form1_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button buttonAñadir;
        private System.Windows.Forms.TextBox textBoxAñadir;
        private System.Windows.Forms.ComboBox comboBoxEstacion;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.RichTextBox richTextBox1;
        private System.Windows.Forms.RichTextBox richTextBox2;
        private System.Windows.Forms.Button buttonConsultar;
        private System.Windows.Forms.ComboBox comboBoxVariable;
        private System.Windows.Forms.TextBox textBoxDisplay;
        private System.Windows.Forms.Button buttonDisplay;
        private System.Windows.Forms.ComboBox comboBoxEstablecer;
        private System.Windows.Forms.Label labelError2;
        private System.Windows.Forms.Label label3;
    }
}

