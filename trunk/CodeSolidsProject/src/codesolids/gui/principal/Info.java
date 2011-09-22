package codesolids.gui.principal;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Color;
import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.FillImage;
import nextapp.echo.app.FillImageBorder;
import nextapp.echo.app.Font;
import nextapp.echo.app.Insets;
import nextapp.echo.app.TextArea;
import nextapp.echo.app.WindowPane;
import codesolids.util.ImageReferenceCache;

public class Info {
	
	public Component initHistoria(){
		
		WindowPane windowPane = new WindowPane();

		windowPane.setMinimumHeight(new Extent(600));
		windowPane.setMinimumWidth(new Extent(500));
		windowPane.setResizable(false);
		windowPane.setMovable(false);
		windowPane.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Fondos/midgard2.jpg")));
		windowPane.setTitleBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/historiaBar.png")));
		windowPane.setBorder(new FillImageBorder(Color.BLACK, new Insets(new Extent(1)), new Insets(new Extent(1))));
		
		TextArea txt = new TextArea();
		txt.setEditable(false);
		
		txt.setHeight(new Extent(600));
		txt.setWidth(new Extent(488));
		txt.setFont(new Font(Font.SANS_SERIF, Font.BOLD, new Extent(14)));
		txt.setForeground(Color.BLACK);
		txt.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Fondos/midgard2.jpg")));
		txt.setAlignment(Alignment.ALIGN_CENTER);
		txt.setText("	Durante muchos años, el poder de la Alquimia regía el mundo de Weyard. " +
				"Gracias al descubrimiento de ese poder, la humanidad alcanzó la cima de la civilización " +
				"y el conocimiento. Pero a la vez que el conocimiento y la sabiduría crecían, también lo " +
				"hacían los sueños y ambiciones. Sueños de conquista, de guerra, de poder ilimitado y de vida" +
				" eterna. Todas estas aspiraciones habrían arrasado Weyard de no ser por unos sabios expertos " +
				"que sellaron este poder en lo más profundo de las entrañas del Templo Sonne, dentro del Monte Aleph." +
				" Tras esto, Weyard se vio privado de su energía elemental y se empezó a destruir a sí mismo poco a" +
				" poco, empezando por el norte. Esto provocó el caos en la aldea más boreal de Weyard, Prox, gobernada " +
				"por el Clan Marte del Norte. Un grupo de emisarios guerreros del Clan Marte del Norte, son enviados por  " +
				"el anciano sabio de la aldea, con la misión de informar a los habitantes de Weyard sobre el peligro que " +
				"se cierne sobre el mundo si la luz de los cuatro faros elementales (Mercurio, Venus, Júpiter y Marte) no " +
				"vuelve a concentrarse en el monte Aleph, ya que mucho tiempo atrás, sus luces fueron selladas para evitar " +
				"que el ser humano destruyese Weyard, al acumular todo el poder que la Alquimia guarda tras de sí. Entre estos " +
				"guerreros emisarios estaban los magos, conocidos por ser los más hábiles al utilizar la psinergia (energía " +
				"derivada de la Alquimia) para usar sus poderes espciales, el poder de la Alquimia fue liberado y con esto las " +
				"aspiraciones de conquista guerra y ser el mas poderoso de todos nuevamente nuevamente. Los clanes se fueron " +
				"formando y haciéndose cada vez mas numerosos. Estos comenzaron a luchar entre si para obtener el poder mas " +
				"fuerte de todo el mundo y poder gobernarlo. Fueron surgiendo nuevos personajes que comenzaron aprender a usar " +
				"la psinergia pero con habilidades distintas de los magos los cuales fueron los guerreros y ladrones, tambien los " +
				"magos se fueron especializando en algunos hechizos para hacerse mas fuertes surgieron los magos de fuego, hielo " +
				"entre otros. La lucha se hace cada vez mas fuerte entre estos clanes y entre cada uno de los personajes que " +
				"prefiere seguir su camino sin unirse a estos clanes y así desafiarlos para ser el mas fuerte de todo  Weyard.\n\n" +
				"Sumergete en este mundo lleno de magia y estrategias para ser el mejor de todos y acabar con tus enemigos.");
		
		windowPane.add(txt);
		windowPane.setModal(true);
		
		return windowPane;
	}

}
