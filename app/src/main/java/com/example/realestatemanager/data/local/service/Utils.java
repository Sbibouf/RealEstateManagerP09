package com.example.realestatemanager.data.local.service;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Philippe on 21/02/2018.
 */

public class Utils {

    /**
     * Conversion d'un prix d'un bien immobilier (Dollars vers Euros)
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     *
     * @param dollars
     * @return
     */
    public static int convertDollarToEuro(int dollars) {
        return (int) Math.round(dollars * 0.812);
    }

    /**
     * Conversion d'un prix d'un bien immobilier (Euros vers Dollars)
     * * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     *
     * @param euros
     * @return
     */
    public static int convertEuroToDollar(int euros) {
        return (int) Math.round(euros / 0.812);
    }

    /**
     * Conversion de la date d'aujourd'hui en un format plus approprié
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     *
     * @return
     */
    public static String getTodayDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        return dateFormat.format(date);
    }

    /**
     * Vérification de la connexion réseau
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     *
     * @param context
     * @return
     */
//    public static Boolean isInternetAvailable(Context context){
//        WifiManager wifi = (WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//        return wifi.isWifiEnabled();
//    }
    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkCapabilities networkCapabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        return networkCapabilities != null &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
    }

    public static String formatCurrency(String value) {
        // Convertir la chaîne en un nombre
        long numericValue = 0;
        try {
            numericValue = Long.parseLong(value);
        } catch (NumberFormatException e) {
            //exception
        }

        // Créer un formateur de devise avec le style approprié
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);

        // Utiliser le format "$#,###" pour exclure les décimales
        String pattern = ((java.text.DecimalFormat) currencyFormat).toPattern().replace(".00", "");
        NumberFormat customFormat = NumberFormat.getNumberInstance(Locale.US);
        ((java.text.DecimalFormat) customFormat).applyPattern(pattern);

        // Formater la valeur et la renvoyer
        return customFormat.format(numericValue);
    }

}
