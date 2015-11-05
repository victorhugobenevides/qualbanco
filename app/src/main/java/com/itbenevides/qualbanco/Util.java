package com.itbenevides.qualbanco;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Onnet on 05/11/2015.
 */
public class Util {

    private String nomeBanco[]=("Banco A.J.Renner S.A.\n" +
            "Banco ABC Brasil S.A.\n" +
            "Banco Alfa S.A.\n" +
            "Banco Alvorada S.A.\n" +
            "Banco Arbi S.A.\n" +
            "Banco Azteca do Brasil S.A.\n" +
            "Banco Banerj S.A.\n" +
            "Banco Bankpar S.A.\n" +
            "Banco Barclays S.A.\n" +
            "Banco BBM S.A.\n" +
            "Banco Beg S.A.\n" +
            "Banco BGN S.A.\n" +
            "Banco BM&F de Serviços de Liquidação e Custódia S.A\n" +
            "Banco BMG S.A.\n" +
            "Banco BNP Paribas Brasil S.A.\n" +
            "Banco Boavista Interatlântico S.A.\n" +
            "Banco Bonsucesso S.A.\n" +
            "Banco Bracce S.A.\n" +
            "Banco Bradesco BBI S.A.\n" +
            "Banco Bradesco Cartões S.A.\n" +
            "Banco Bradesco Financiamentos S.A.\n" +
            "Banco Bradesco S.A.\n" +
            "Banco Brascan S.A.\n" +
            "Banco BRJ S.A.\n" +
            "Banco BTG Pactual S.A.\n" +
            "Banco BVA S.A.\n" +
            "Banco Cacique S.A.\n" +
            "Banco Caixa Geral - Brasil S.A.\n" +
            "Banco Capital S.A.\n" +
            "Banco Cargill S.A.\n" +
            "Banco Citibank S.A.\n" +
            "Banco Citicard S.A.\n" +
            "Banco Clássico S.A.\n" +
            "Banco CNH Capital S.A.\n" +
            "Banco Comercial e de Investimento Sudameris S.A.\n" +
            "Banco Cooperativo do Brasil S.A.\n" +
            "Banco Cooperativo Sicredi S.A.\n" +
            "Banco CR2 S.A.\n" +
            "Banco Credibel S.A.\n" +
            "Banco Credit Agricole Brasil S.A.\n" +
            "Banco Credit Suisse (Brasil) S.A.\n" +
            "Banco Cruzeiro do Sul S.A.\n" +
            "Banco Cédula S.A.\n" +
            "Banco da Amazônia S.A.\n" +
            "Banco da China Brasil S.A.\n" +
            "Banco Daimlerchrysler S.A.\n" +
            "Banco Daycoval S.A.\n" +
            "Banco de La Nacion Argentina\n" +
            "Banco de La Provincia de Buenos Aires\n" +
            "Banco de La Republica Oriental del Uruguay\n" +
            "Banco de Lage Landen Brasil S.A.\n" +
            "Banco de Pernambuco S.A. - BANDEPE\n" +
            "Banco de Tokyo-Mitsubishi UFJ Brasil S.A.\n" +
            "Banco Dibens S.A.\n" +
            "Banco do Brasil S.A.\n" +
            "Banco do Estado de Sergipe S.A.\n" +
            "Banco do Estado do Pará S.A.\n" +
            "Banco do Estado do Piauí S.A.\n" +
            "Banco do Estado do Rio Grande do Sul S.A.\n" +
            "Banco do Nordeste do Brasil S.A.\n" +
            "Banco Fator S.A.\n" +
            "Banco Fiat S.A.\n" +
            "Banco Fibra S.A.\n" +
            "Banco Ficsa S.A.\n" +
            "Banco Ford S.A.\n" +
            "Banco GE Capital S.A.\n" +
            "Banco Gerdau S.A.\n" +
            "Banco GMAC S.A.\n" +
            "Banco Guanabara S.A.\n" +
            "Banco Honda S.A.\n" +
            "Banco Ibi S.A. Banco Múltiplo\n" +
            "Banco IBM S.A.\n" +
            "Banco Industrial do Brasil S.A.\n" +
            "Banco Industrial e Comercial S.A.\n" +
            "Banco Indusval S.A.\n" +
            "Banco Intercap S.A.\n" +
            "Banco Intermedium S.A.\n" +
            "Banco Investcred Unibanco S.A.\n" +
            "Banco Itaucred Financiamentos S.A.\n" +
            "Banco Itaú BBA S.A.\n" +
            "Banco ItaúBank S.A\n" +
            "Banco J. P. Morgan S.A.\n" +
            "Banco J. Safra S.A.\n" +
            "Banco John Deere S.A.\n" +
            "Banco KDB S.A.\n" +
            "Banco KEB do Brasil S.A.\n" +
            "Banco Luso Brasileiro S.A.\n" +
            "Banco Matone S.A.\n" +
            "Banco Maxinvest S.A.\n" +
            "Banco Mercantil do Brasil S.A.\n" +
            "Banco Modal S.A.\n" +
            "Banco Moneo S.A.\n" +
            "Banco Morada S.A.\n" +
            "Banco Morgan Stanley S.A.\n" +
            "Banco Máxima S.A.\n" +
            "Banco Opportunity S.A.\n" +
            "Banco Ourinvest S.A.\n" +
            "Banco Panamericano S.A.\n" +
            "Banco Paulista S.A.\n" +
            "Banco Pecúnia S.A.\n" +
            "Banco Petra S.A.\n" +
            "Banco Pine S.A.\n" +
            "Banco Porto Seguro S.A.\n" +
            "Banco Pottencial S.A.\n" +
            "Banco Prosper S.A.\n" +
            "Banco PSA Finance Brasil S.A.\n" +
            "Banco Rabobank International Brasil S.A.\n" +
            "Banco Randon S.A.\n" +
            "Banco Real S.A.\n" +
            "Banco Rendimento S.A.\n" +
            "Banco Ribeirão Preto S.A.\n" +
            "Banco Rodobens S.A.\n" +
            "Banco Rural Mais S.A.\n" +
            "Banco Rural S.A.\n" +
            "Banco Safra S.A.\n" +
            "Santander (Brasil) S.A.\n" +
            "Banco Schahin S.A.\n" +
            "Banco Semear S.A.\n" +
            "Banco Simples S.A.\n" +
            "Banco Société Générale Brasil S.A.\n" +
            "Banco Sofisa S.A.\n" +
            "Banco Standard de Investimentos S.A.\n" +
            "Banco Sumitomo Mitsui Brasileiro S.A.\n" +
            "Banco Topázio S.A.\n" +
            "Banco Toyota do Brasil S.A.\n" +
            "Banco Tricury S.A.\n" +
            "Banco Triângulo S.A.\n" +
            "Banco Volkswagen S.A.\n" +
            "Banco Volvo (Brasil) S.A.\n" +
            "Banco Votorantim S.A.\n" +
            "Banco VR S.A.\n" +
            "Banco WestLB do Brasil S.A.\n" +
            "BANESTES S.A. Banco do Estado do Espírito Santo\n" +
            "Banif-Banco Internacional do Funchal (Brasil)S.A.\n" +
            "Bank of America Merrill Lynch Banco Múltiplo S.A.\n" +
            "BankBoston N.A.\n" +
            "BB Banco Popular do Brasil S.A.\n" +
            "BES Investimento do Brasil S.A.-Banco de Investimento\n" +
            "BPN Brasil Banco Múltiplo S.A.\n" +
            "BRB - Banco de Brasília S.A.\n" +
            "Brickell S.A. Crédito, financiamento e Investimento-\n" +
            "Caixa Econômica Federal\n" +
            "Citibank N.A.\n" +
            "Concórdia Banco S.A.\n" +
            "Cooperativa Central de Crédito Noroeste Brasileiro Ltda.\n" +
            "Cooperativa Central de Crédito Urbano-CECRED\n" +
            "Cooperativa Central de Economia e Credito Mutuo das Unicreds\n" +
            "Cooperativa Central de Economia e Crédito Mutuo das Unicreds\n" +
            "Cooperativa de Crédito Rural da Região de Mogiana\n" +
            "Cooperativa Unicred Central Santa Catarina\n" +
            "Credicorol Cooperativa de Crédito Rural\n" +
            "Deutsche Bank S.A. - Banco Alemão\n" +
            "Dresdner Bank Brasil S.A. - Banco Múltiplo\n" +
            "Goldman Sachs do Brasil Banco Múltiplo S.A.\n" +
            "Hipercard Banco Múltiplo S.A.\n" +
            "HSBC Bank Brasil S.A.\n" +
            "HSBC Finance (Brasil) S.A.\n" +
            "ING Bank N.V.\n" +
            "Itaú Unibanco Holding S.A.\n" +
            "Itaú Unibanco S.A.\n" +
            "JBS Banco S.A.\n" +
            "JPMorgan Chase Bank\n" +
            "Natixis Brasil S.A. Banco Múltiplo\n" +
            "NBC Bank Brasil S.A. - Banco Múltiplo\n" +
            "OBOE Crédito Financiamento e Investimento S.A.\n" +
            "Paraná Banco S.A.\n" +
            "UNIBANCO - União de Bancos Brasileiros S.A.\n" +
            "Unicard Banco Múltiplo S.A.\n" +
            "Unicred Central do Rio Grande do Sul\n" +
            "Unicred Norte do Paraná").split("\n");

    private String codigoBanco[]=("654\n" +
            "246\n" +
            "025\n" +
            "641\n" +
            "213\n" +
            "019\n" +
            "029\n" +
            "000\n" +
            "740\n" +
            "107\n" +
            "031\n" +
            "739\n" +
            "096\n" +
            "318\n" +
            "752\n" +
            "248\n" +
            "218\n" +
            "065\n" +
            "036\n" +
            "204\n" +
            "394\n" +
            "237\n" +
            "225\n" +
            "M15\n" +
            "208\n" +
            "044\n" +
            "263\n" +
            "473\n" +
            "412\n" +
            "040\n" +
            "745\n" +
            "M08\n" +
            "241\n" +
            "M19\n" +
            "215\n" +
            "756\n" +
            "748\n" +
            "075\n" +
            "721\n" +
            "222\n" +
            "505\n" +
            "229\n" +
            "266\n" +
            "003\n" +
            "083-3\n" +
            "M21\n" +
            "707\n" +
            "300\n" +
            "495\n" +
            "494\n" +
            "M06\n" +
            "024\n" +
            "456\n" +
            "214\n" +
            "001\n" +
            "047\n" +
            "037\n" +
            "039\n" +
            "041\n" +
            "004\n" +
            "265\n" +
            "M03\n" +
            "224\n" +
            "626\n" +
            "M18\n" +
            "233\n" +
            "734\n" +
            "M07\n" +
            "612\n" +
            "M22\n" +
            "063\n" +
            "M11\n" +
            "604\n" +
            "320\n" +
            "653\n" +
            "630\n" +
            "077-9\n" +
            "249\n" +
            "M09\n" +
            "184\n" +
            "479\n" +
            "376\n" +
            "074\n" +
            "217\n" +
            "076\n" +
            "757\n" +
            "600\n" +
            "212\n" +
            "M12\n" +
            "389\n" +
            "746\n" +
            "M10\n" +
            "738\n" +
            "066\n" +
            "243\n" +
            "045\n" +
            "M17\n" +
            "623\n" +
            "611\n" +
            "613\n" +
            "094-2\n" +
            "643\n" +
            "724\n" +
            "735\n" +
            "638\n" +
            "M24\n" +
            "747\n" +
            "088-4\n" +
            "356\n" +
            "633\n" +
            "741\n" +
            "M16\n" +
            "072\n" +
            "453\n" +
            "422\n" +
            "033\n" +
            "250\n" +
            "743\n" +
            "749\n" +
            "366\n" +
            "637\n" +
            "012\n" +
            "464\n" +
            "082-5\n" +
            "M20\n" +
            "M13\n" +
            "634\n" +
            "M14\n" +
            "M23\n" +
            "655\n" +
            "610\n" +
            "370\n" +
            "021\n" +
            "719\n" +
            "755\n" +
            "744\n" +
            "073\n" +
            "078\n" +
            "069\n" +
            "070\n" +
            "092-2\n" +
            "104\n" +
            "477\n" +
            "081-7\n" +
            "097-3\n" +
            "085-x\n" +
            "099-x\n" +
            "090-2\n" +
            "089-2\n" +
            "087-6\n" +
            "098-1\n" +
            "487\n" +
            "751\n" +
            "064\n" +
            "062\n" +
            "399\n" +
            "168\n" +
            "492\n" +
            "652\n" +
            "341\n" +
            "079\n" +
            "488\n" +
            "014\n" +
            "753\n" +
            "086-8\n" +
            "254\n" +
            "409\n" +
            "230\n" +
            "091-4\n" +
            "084").split("\n");




    public void criaDados(SQLiteDatabase sqlitedatabase){




        sqlitedatabase.beginTransaction();
        try {


            for(int i=0;i<codigoBanco.length;i++){
                Banco banco = new Banco();
                banco.setId_(i);
                banco.setCodigobanco(codigoBanco[i]);
                banco.setNome(nomeBanco[i]);
                banco.setId_(sqlitedatabase.insert(banco.getTabela(), null, banco.getContentValues()));
            }
            sqlitedatabase.setTransactionSuccessful();
        }catch (Exception e){

        }finally {
            sqlitedatabase.endTransaction();
        }



    }




}
