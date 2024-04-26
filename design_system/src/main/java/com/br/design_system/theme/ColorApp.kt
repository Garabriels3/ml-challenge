package com.br.design_system.theme

import androidx.compose.ui.graphics.Color

object ColorApp {
    // Cores Primárias
    val primary = Color(0xFFFECC00)  // Amarelo Mercado Livre
    val primaryDark = Color(0xFFC7A600)  // Amarelo mais escuro para modo noturno

    // Cores de Texto e Ícones
    val textOnPrimary = Color(0xFF333333)  // Escuro para textos sobre fundo amarelo
    val textOnBackground = Color(0xFF000000)  // Preto para textos no modo diurno
    val textOnBackgroundDark = Color(0xFFFFFFFF)  // Branco para textos no modo noturno
    val textOnSurface = Color(0xFF000000)  // Preto para textos sobre superfície
    val textOnSurfaceDark = Color(0xFFFFFFFF)  // Branco para textos sobre superfície no modo noturno

    // Cores de Alerta
    val error = Color(0xFFD32F2F)  // Vermelho para erros
    val onError = Color(0xFFFFFFFF)  // Branco para texto sobre erros

    // Cores de Sucesso
    val success = Color(0xFF388E3C)  // Verde para sucesso
    val onSuccess = Color(0xFFFFFFFF)  // Branco para texto sobre sucesso

    // Neutros e Fundos
    val background = Color(0xFFFFFFFF)  // Branco para fundo no modo diurno
    val backgroundDark = Color(0xFF121212)  // Fundo escuro para modo noturno
    val surface = Color(0xFFFFFFFF)  // Superfície de cartões no modo diurno
    val surfaceDark = Color(0xFF1E1E1E)  // Superfície de cartões no modo noturno

    // Icones
    val icon = Color(0xFFB6B5B5)  // Cor padrão para ícones
    val iconDark = Color(0xFFFFFFFF)  // Cor padrão para ícones no modo noturno

    // Outros
    val outline = Color(0xFFBDBDBD)  // Bordas e contornos no modo diurno
    val outlineDark = Color(0xFF757575)  // Bordas e contornos no modo noturno
}
