import { Titulo } from '@/components/ui/titulo'
import Featurecourses from '@/components/featuredcourses'
import dataScienceF from '@/assets/dataSciencef.png'
import gestaoW from '@/assets/gestaoW.png'
import redesW from '@/assets/redesW.png'

export default function Index() {
  const courses = [
    {
      title: 'Data Science',
      text: 'Em um mundo cada vez mais movido por dados, Data Science se tornou uma das áreas mais promissoras. Você aprenderá como coletar, analisar e interpretar grandes volumes de dados para gerar insights poderosos que podem mudar a forma como as decisões são tomadas. Com uma combinação de estatísticas, programação e machine learning, você estará preparado para transformar dados em conhecimento estratégico e conquistar o mercado de trabalho.',
      src: dataScienceF,
      colorTitle: 'text-custom-dataScience-index',
      colorInfo: 'bg-custom-dataScience-index',
    },
    {
      title: 'Redes',
      text: 'O mundo está cada vez mais conectado, e dominar Redes de Computadores é essencial para quem quer estar à frente no mercado de TI. Você vai entender como os sistemas de comunicação funcionam, desde a conexão entre computadores até a criação de redes seguras e eficientes. Se você já pensou em montar sua própria rede ou gerenciar infraestruturas complexas, este é o caminho para adquirir as habilidades práticas que as empresas mais precisam.',
      order: ['text', 'list', 'image'],
      src: redesW,
      colorTitle: 'text-custom-redes-index',
      colorInfo: 'bg-custom-redes-index',
    },
    {
      title: 'Gestão',
      text:"Para ser um líder de sucesso, é preciso mais do que apenas conhecimento técnico — você também precisa entender como gerenciar recursos, equipes e projetos de forma eficaz. Em Gestão, você aprenderá a planejar, organizar e executar estratégias que potencializam os resultados de qualquer negócio. Seja você um empreendedor ou alguém em busca de crescimento profissional, o domínio da gestão pode transformar suas ideias em ações de sucesso.",
      src: gestaoW,
      colorTitle: 'text-custom-gestao-index',
      colorInfo: 'bg-custom-gestao-index',
    },
  ]
  return (
    <>
      <Titulo titulo={'IMO'} />

      <div className="bg-custom-dark-purple min-h-screen px-4 md:px-8 flex flex-col">
        {courses.map((item, index) => {
          return (
            <>
              <Featurecourses
                key={index}
                title={item.title}
                text={item.text}
                order={item.order}
                src={item.src}
                colorTitle={item.colorTitle}
                colorInfo={item.colorInfo}
              />
              <div className="h-[1px] bg-white w-full max-w-[900px] m-auto my-4"></div>
            </>
          )
        })}
      </div>
    </>
  )
}
