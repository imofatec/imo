package com.imo.backend.lib.image;

import com.imo.backend.contexts.journey_tracking.progress_milestone.ProgressMilestone;
import com.imo.backend.contexts.journey_tracking.progress_milestone.lib.ProgressMilestoneImageRenderer;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Service;

@Service
public class Java2DProgressMilestoneImageRenderer implements ProgressMilestoneImageRenderer {

  // ── Canvas ────────────────────────────────────────────────────────────────
  private static final int W = 1200;
  private static final int H = 630;

  // ── Card geometry (derivados, não mudar individualmente) ──────────────────
  private static final int CARD_PAD = 48; // margem externa left/right
  private static final int CARD_TOP = 32; // margem externa top/bottom
  private static final int CARD_X = CARD_PAD;
  private static final int CARD_Y = CARD_TOP;
  private static final int CARD_W = W - CARD_PAD * 2; // 1104
  private static final int CARD_H = H - CARD_TOP * 2; // 566
  private static final int CARD_RADIUS = 28;
  private static final int TOP_BAR_H = 12; // faixa azul no topo

  // ── Inner content ─────────────────────────────────────────────────────────
  private static final int INNER_PAD = 52; // padding interno left
  private static final int CONTENT_X = CARD_X + INNER_PAD; // 100
  private static final int CONTENT_TOP = CARD_Y + TOP_BAR_H + 36; // 80 (topo do conteúdo)
  private static final int CONTENT_BOT = CARD_Y + CARD_H - 40; // 558 (fundo do conteúdo)

  // Divisão esquerda / direita: 60% / 40%
  private static final int DIV_X = CARD_X + (int) (CARD_W * 0.60); // 762
  private static final int LEFT_W = DIV_X - CONTENT_X - 16; // 646
  private static final int RIGHT_CX =
      DIV_X + (CARD_X + CARD_W - DIV_X) / 2; // centro da col. direita

  // ── Paleta ────────────────────────────────────────────────────────────────
  private static final Color PAGE_BG = new Color(0x0B, 0x0F, 0x1A);
  private static final Color CARD_BG = new Color(0x11, 0x18, 0x27);
  private static final Color CARD_BG_SOFT = new Color(0x0D, 0x15, 0x26);
  private static final Color CARD_BORDER = new Color(0x1E, 0x2D, 0x4A);
  private static final Color ACCENT = new Color(0x3B, 0x82, 0xF6);
  private static final Color ACCENT_DIM = new Color(0x3B, 0x82, 0xF6, 28);
  private static final Color ACCENT_MID = new Color(0x3B, 0x82, 0xF6, 70);
  private static final Color BADGE_BG = new Color(0x1A, 0x27, 0x44);
  private static final Color TEXT_PRIMARY = new Color(0xF0, 0xF4, 0xFF);
  private static final Color TEXT_SECOND = new Color(0x8A, 0xA4, 0xC8);
  private static final Color TEXT_MUTED = new Color(0x4A, 0x6A, 0x96);
  private static final Color TEXT_FOOTER = new Color(0x2E, 0x4A, 0x6E);
  private static final Color TRACK_BG = new Color(0x1E, 0x2D, 0x4A);
  private static final Color SHADOW = new Color(0x02, 0x06, 0x10, 110);

  private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  private final Font spaceGrotesk;
  private final Font jetBrainsMono;

  public Java2DProgressMilestoneImageRenderer() {
    this.spaceGrotesk =
        loadFont("/static/fonts/SpaceGrotesk.ttf", new Font(Font.SANS_SERIF, Font.PLAIN, 12));
    this.jetBrainsMono =
        loadFont(
            "/static/fonts/JetBrainsMono-Regular.ttf", new Font(Font.MONOSPACED, Font.PLAIN, 12));
  }

  @Override
  public byte[] execute(ProgressMilestone milestone) {
    BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = img.createGraphics();
    try {
      configure(g);
      drawBackground(g);
      drawCard(g, milestone);
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      ImageIO.write(img, "png", out);
      return out.toByteArray();
    } catch (IOException e) {
      throw new RuntimeException("Erro ao gerar a imagem do marco", e);
    } finally {
      g.dispose();
    }
  }

  // ── Setup ─────────────────────────────────────────────────────────────────

  private void configure(Graphics2D g) {
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g.setRenderingHint(
        RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g.setRenderingHint(
        RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    g.setRenderingHint(
        RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
  }

  // ── Background ────────────────────────────────────────────────────────────

  private void drawBackground(Graphics2D g) {
    g.setColor(PAGE_BG);
    g.fillRect(0, 0, W, H);
    g.setColor(ACCENT_DIM);
    g.fillOval(-160, -200, 600, 600);
    g.fillOval(W - 380, H - 320, 560, 560);
  }

  // ── Card shell ────────────────────────────────────────────────────────────

  private void drawCard(Graphics2D g, ProgressMilestone milestone) {
    // Sombra
    g.setColor(SHADOW);
    g.fillRoundRect(CARD_X + 10, CARD_Y + 16, CARD_W, CARD_H, CARD_RADIUS, CARD_RADIUS);

    // Fundo
    g.setColor(CARD_BG);
    g.fillRoundRect(CARD_X, CARD_Y, CARD_W, CARD_H, CARD_RADIUS, CARD_RADIUS);

    // Faixa azul topo
    g.setColor(ACCENT);
    g.fillRoundRect(CARD_X, CARD_Y, CARD_W, TOP_BAR_H + 6, CARD_RADIUS, CARD_RADIUS);
    g.fillRect(CARD_X, CARD_Y + 6, CARD_W, TOP_BAR_H);

    // Borda
    g.setStroke(new BasicStroke(1.5f));
    g.setColor(CARD_BORDER);
    g.drawRoundRect(CARD_X, CARD_Y, CARD_W, CARD_H, CARD_RADIUS, CARD_RADIUS);

    drawLeftColumn(g, milestone);
    drawRightDecorator(g, milestone);
  }

  // ── Coluna esquerda ───────────────────────────────────────────────────────
  //
  // Posições Y absolutas (baseline do texto ou topo do elemento):
  //
  // CONTENT_TOP = 80
  // Logo baseline = 80 + 14 = 94
  // Badge top = 94 + 20 = 114 (20px gap logo→badge)
  // Título baseline linha1 = 114+30+20= 164 (badge h=30, gap=20)
  // Título baseline linha2 = 164 + 54 = 218 (line-height 54px — só se 2 linhas)
  // Autor baseline = título_end + 16
  // Divisor top = autor_baseline + 22
  // Pills top = divisor + 20
  // Barra top = pills + 34 + 18 = pills + 52
  // Footer baseline = CONTENT_BOT = 558

  private void drawLeftColumn(Graphics2D g, ProgressMilestone milestone) {
    final int x = CONTENT_X;
    final int w = LEFT_W;

    // ── Logo (baseline)
    int logoBaseline = CONTENT_TOP + 14;
    drawLogo(g, x, logoBaseline);

    // ── Badge
    int badgeTop = logoBaseline + 20;
    int badgeBottom = drawBadge(g, x, badgeTop, "CURSO CONCLUÍDO");

    // ── Título do curso
    int titleTop = badgeBottom + 20;
    int titleEnd =
        drawWrapped(
            g,
            safe(milestone.getCourseNameSnapshot()),
            spaceGrotesk.deriveFont(Font.BOLD, 48f),
            TEXT_PRIMARY,
            x,
            titleTop,
            w,
            54,
            2);

    // ── Autor
    int autorBaseline = titleEnd + 18;
    g.setFont(jetBrainsMono.deriveFont(Font.PLAIN, 15f));
    g.setColor(TEXT_MUTED);
    g.drawString("por " + safe(milestone.getAuthorNameSnapshot()), x, autorBaseline);

    // ── Divisor
    int dividerTop = autorBaseline + 22;
    g.setColor(CARD_BORDER);
    g.fillRect(x, dividerTop, w, 1);

    // ── Pills
    int pillsTop = dividerTop + 20;
    drawInfoPills(g, milestone, x, pillsTop, w);

    // ── Barra de progresso
    int barTop = pillsTop + 34 + 18; // altura pill + gap
    drawProgressBar(g, x, barTop, w, milestone.getCompletionPercentageSnapshot());

    // ── Footer (fixo no fundo)
    int footerLineY = CONTENT_BOT - 28;
    g.setColor(CARD_BORDER);
    g.fillRect(x, footerLineY, w, 1);
    g.setFont(jetBrainsMono.deriveFont(Font.PLAIN, 12f));
    g.setColor(TEXT_FOOTER);
    g.drawString("// Registro de progresso, não certificação de proficiência.", x, CONTENT_BOT);
  }

  // ── Logo ──────────────────────────────────────────────────────────────────

  private void drawLogo(Graphics2D g, int x, int baseline) {
    int h = 16, bw = 14;
    int[] xs = {x, x, x + bw};
    int[] ys = {baseline - h / 2, baseline + h / 2, baseline};
    g.setColor(ACCENT);
    g.fillPolygon(xs, ys, 3);
    g.setFont(spaceGrotesk.deriveFont(Font.BOLD, 18f));
    g.setColor(TEXT_PRIMARY);
    g.drawString("IMO", x + bw + 9, baseline + 6);
  }

  // ── Badge — retorna o Y do fundo do badge ─────────────────────────────────

  private int drawBadge(Graphics2D g, int x, int y, String text) {
    Font font = jetBrainsMono.deriveFont(Font.PLAIN, 13f);
    FontMetrics fm = g.getFontMetrics(font);
    int hPad = 14, bh = 30;
    int bw = fm.stringWidth(text) + hPad * 2 + 22;

    g.setColor(BADGE_BG);
    g.fillRoundRect(x, y, bw, bh, 10, 10);
    g.setStroke(new BasicStroke(1f));
    g.setColor(ACCENT);
    g.drawRoundRect(x, y, bw, bh, 10, 10);

    // Checkmark
    g.setFont(spaceGrotesk.deriveFont(Font.BOLD, 15f));
    g.setColor(ACCENT);
    g.drawString("✓", x + hPad, y + 21);

    g.setFont(font);
    g.drawString(text, x + hPad + 18, y + 21);

    return y + bh; // fundo do badge
  }

  // ── drawWrapped — retorna Y da baseline da última linha + lineH ───────────

  private int drawWrapped(
      Graphics2D g,
      String text,
      Font font,
      Color color,
      int x,
      int y,
      int maxW,
      int lineH,
      int maxLines) {

    g.setFont(font);
    g.setColor(color);
    FontMetrics fm = g.getFontMetrics(font);
    List<String> lines = wrap(text, fm, maxW, maxLines);

    // y aqui é a baseline da primeira linha
    int baseline = y + lineH; // primeira baseline
    for (String line : lines) {
      g.drawString(line, x, baseline);
      baseline += lineH;
    }
    return baseline; // Y logo após o bloco de texto
  }

  // ── Info pills ────────────────────────────────────────────────────────────

  private void drawInfoPills(Graphics2D g, ProgressMilestone milestone, int x, int y, int w) {
    String aulas =
        milestone.getWatchedLessonsCountSnapshot()
            + "/"
            + milestone.getTotalLessonsCountSnapshot()
            + " aulas";
    String inicio = "Início: " + toDate(milestone.getCourseStartedAtSnapshot());
    String conclusao = "Conclusão: " + toDate(milestone.getCourseFinishedAtSnapshot());

    int pillH = 34, gap = 10, cursor = x;

    for (String label : new String[] {aulas, inicio, conclusao}) {
      Font font = jetBrainsMono.deriveFont(Font.PLAIN, 13f);
      FontMetrics fm = g.getFontMetrics(font);
      int pillW = fm.stringWidth(label) + 24;

      if (cursor + pillW > x + w) break;

      g.setColor(CARD_BG_SOFT);
      g.fillRoundRect(cursor, y, pillW, pillH, 16, 16);
      g.setStroke(new BasicStroke(1f));
      g.setColor(CARD_BORDER);
      g.drawRoundRect(cursor, y, pillW, pillH, 16, 16);

      g.setFont(font);
      g.setColor(TEXT_SECOND);
      g.drawString(label, cursor + 12, y + 22);

      cursor += pillW + gap;
    }
  }

  // ── Barra de progresso ────────────────────────────────────────────────────

  private void drawProgressBar(Graphics2D g, int x, int y, int w, int pct) {
    int trackH = 10, r = 5;

    g.setColor(TRACK_BG);
    g.fillRoundRect(x, y, w, trackH, r * 2, r * 2);

    int fillW = (int) Math.round(w * Math.min(pct, 100) / 100.0);
    if (fillW > 0) {
      g.setPaint(new GradientPaint(x, y, ACCENT, x + fillW, y, new Color(0x60, 0xA5, 0xFA)));
      g.fillRoundRect(x, y, fillW, trackH, r * 2, r * 2);
    }

    // Pill % alinhado à direita, abaixo da barra
    String label = pct + "%";
    Font font = spaceGrotesk.deriveFont(Font.BOLD, 14f);
    FontMetrics fm = g.getFontMetrics(font);
    int lblW = fm.stringWidth(label) + 18;
    int lblH = 24;
    int lblX = x + w - lblW;
    int lblY = y + trackH + 8;

    g.setColor(BADGE_BG);
    g.fillRoundRect(lblX, lblY, lblW, lblH, 6, 6);
    g.setFont(font);
    g.setColor(ACCENT);
    g.drawString(label, lblX + 9, lblY + 17);
  }

  // ── Círculo decorativo (direita) ──────────────────────────────────────────

  private void drawRightDecorator(Graphics2D g, ProgressMilestone milestone) {
    int cx = RIGHT_CX;
    int cy = CARD_Y + CARD_H / 2; // centro vertical do card
    int pct = milestone.getCompletionPercentageSnapshot();

    int outer = 128;
    int ring = 96;

    // Glows concêntricos
    int[] alphas = {8, 14, 20, 28};
    int[] radii = {outer + 56, outer + 38, outer + 20, outer + 6};
    for (int i = 0; i < alphas.length; i++) {
      g.setColor(new Color(0x3B, 0x82, 0xF6, alphas[i]));
      int gr = radii[i];
      g.fillOval(cx - gr, cy - gr, gr * 2, gr * 2);
    }

    // Anel externo
    g.setStroke(new BasicStroke(1f));
    g.setColor(ACCENT_MID);
    g.drawOval(cx - outer, cy - outer, outer * 2, outer * 2);

    // Arco — trilha
    g.setStroke(new BasicStroke(11f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
    g.setColor(TRACK_BG);
    g.draw(new Arc2D.Float(cx - ring, cy - ring, ring * 2, ring * 2, 90, -360, Arc2D.OPEN));

    // Arco — fill
    float sweep = 360f * pct / 100f;
    g.setPaint(
        new GradientPaint(cx - ring, cy, ACCENT, cx + ring, cy, new Color(0x60, 0xA5, 0xFA)));
    g.draw(new Arc2D.Float(cx - ring, cy - ring, ring * 2, ring * 2, 90, -sweep, Arc2D.OPEN));

    // Disco interno escuro
    int inner = ring - 16;
    g.setColor(CARD_BG_SOFT);
    g.fillOval(cx - inner, cy - inner, inner * 2, inner * 2);

    // Checkmark
    g.setStroke(new BasicStroke(7f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
    g.setColor(ACCENT);
    int[] ckX = {cx - 26, cx - 4, cx + 28};
    int[] ckY = {cy, cy + 22, cy - 22};
    g.drawPolyline(ckX, ckY, 3);

    // Sparks
    drawSpark(g, cx + outer + 22, cy - outer + 10, 7);
    drawSpark(g, cx + outer + 6, cy + outer - 26, 5);
    drawSpark(g, cx - outer - 14, cy - 24, 4);
  }

  private void drawSpark(Graphics2D g, int x, int y, int size) {
    g.setStroke(new BasicStroke(1.5f));
    g.setColor(new Color(0x3B, 0x82, 0xF6, 120));
    g.drawLine(x, y - size, x, y + size);
    g.drawLine(x - size, y, x + size, y);
    int d = (int) (size * 0.55);
    g.setColor(new Color(0x3B, 0x82, 0xF6, 55));
    g.drawLine(x - d, y - d, x + d, y + d);
    g.drawLine(x + d, y - d, x - d, y + d);
  }

  // ── Text utilities ────────────────────────────────────────────────────────

  private List<String> wrap(String text, FontMetrics fm, int maxW, int maxLines) {
    String t = safe(text).trim();
    if (t.isEmpty()) return List.of("");

    String[] words = t.split("\\s+");
    List<String> lines = new ArrayList<>();
    String current = "";

    for (String word : words) {
      if (current.isEmpty()) {
        current = word;
        continue;
      }
      String candidate = current + " " + word;
      if (fm.stringWidth(candidate) <= maxW) {
        current = candidate;
        continue;
      }
      lines.add(current);
      current = word;
    }
    if (!current.isEmpty()) lines.add(current);

    if (lines.size() <= maxLines) return lines;

    List<String> result = new ArrayList<>(lines.subList(0, maxLines - 1));
    String remainder = String.join(" ", lines.subList(maxLines - 1, lines.size()));
    result.add(ellipsize(remainder, fm, maxW));
    return result;
  }

  private String ellipsize(String text, FontMetrics fm, int maxW) {
    String v = safe(text).trim();
    if (fm.stringWidth(v) <= maxW) return v;
    String ellipsis = "...";
    int end = v.length();
    while (end > 0) {
      String c = v.substring(0, end).trim() + ellipsis;
      if (fm.stringWidth(c) <= maxW) return c;
      end--;
    }
    return ellipsis;
  }

  // ── Font loader ───────────────────────────────────────────────────────────

  private Font loadFont(String resource, Font fallback) {
    try (InputStream is = getClass().getResourceAsStream(resource)) {
      if (is == null) return fallback;
      return Font.createFont(Font.TRUETYPE_FONT, is);
    } catch (FontFormatException | IOException e) {
      return fallback;
    }
  }

  private String safe(String v) {
    return v == null ? "" : v;
  }

  private String toDate(LocalDateTime v) {
    return v == null ? "--/--/----" : DATE_FMT.format(v);
  }
}
