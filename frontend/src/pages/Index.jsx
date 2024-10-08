import { Titulo } from '@/components/ui/titulo'
import Featurecourses from '@/components/featuredcourses'
import dataScienceF from '@/assets/dataSciencef.png'
import gestaoW from '@/assets/gestaoW.png'
import redesW from '@/assets/redesW.png'

export default function Index() {
  const courses = [
    { title: 'Data Science', src: dataScienceF },
    { title: 'Redes', order: ['text', 'list', 'image'], src: redesW },
    { title: 'Gestão', src: gestaoW },
  ]
  return (
    <>
      <Titulo titulo={'IMO'} />

      <div className="bg-custom-dark-purple min-h-screen px-4 md:px-8 flex flex-col">
        {courses.map((item, index, i) => {
          return (
            <>
              <Featurecourses
                key={index}
                title={item.title}
                text={item.text}
                order={item.order}
                src={item.src}
              />
              <div className="h-[1px] bg-white w-full max-w-[900px] m-auto my-4"></div>
            </>
          )
        })}
      </div>
    </>
  )
}
