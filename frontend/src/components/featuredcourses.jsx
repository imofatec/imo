import DefaultImage from '@/assets/defaultImage.svg'

export default function FeatureCourses({
  title = 'Default',
  text = 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Illo temporibus eaque tempore neque dolores fuga quo debitis, fugiat repudiandae illum, similique magnam ea, rem sit explicabo optio? Corrupti, ad sit?',
  src = DefaultImage,
  order = ['image', 'text', 'list'],
  colorTitle,
  colorInfo,
}) {
  const renderElement = (element) => {
    switch (element) {
      case 'image':
        return (
          <img
            src={src}
            alt="Imagem do curso"
            className="w-full max-w-xs md:max-w-sm lg:max-w-md"
          />
        )
      case 'text':
        return (
          <div className="flex flex-col gap-4 md:gap-8 text-center md:text-left">
            <h1 className="text-4xl md:text-5xl">Aprenda</h1>
            <h1 className={`text-4xl md:text-5xl ${colorTitle}`}>{title}</h1>
            <p className="text-lg md:text-2xl w-full max-w-xl">{text}</p>
          </div>
        )
      case 'list':
        return (
          <div>
            <ul className="text-lg md:text-xl space-y-16">
              <li className="flex items-center space-x-4">
                <div className={`w-4 h-2.5 ${colorInfo} rounded-sm`}></div>
                <span>Lorem ipsum porttitor eros mattis</span>
              </li>
              <li className="flex items-center space-x-4">
                <div className={`w-4 h-2.5 ${colorInfo} rounded-sm`}></div>
                <span>Lorem ipsum porttitor eros mattis</span>
              </li>
              <li className="flex items-center space-x-4">
                <div className={`w-4 h-2.5 ${colorInfo} rounded-sm`}></div>
                <span>Lorem ipsum porttitor eros mattis</span>
              </li>
            </ul>
          </div>
        )
      default:
        return null
    }
  }

  return (
    <div className="min-h-[34.438rem] flex flex-col md:flex-row justify-evenly items-center text-white space-y-8 md:space-y-0 md:space-x-8">
      {order.map((element, index) => (
        <div key={index}>{renderElement(element)}</div>
      ))}
    </div>
  )
}
