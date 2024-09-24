import { Separator } from '../separator'

export default function LessonDescription({ descr }) {
  descr =
    'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Velit ratione veritatis vitae a voluptas quibusdam vero veniam, molestiae, ducimus natus quasi alias laboriosam officia eos minus. Est temporibus dolores hic. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Velit ratione veritatis vitae a voluptas quibusdam vero veniam, molestiae, ducimus natus quasi alias laboriosam officia eos minus. Est temporibus dolores hic. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Velit ratione veritatis vitae a voluptas quibusdam vero veniam, molestiae, ducimus natus quasi alias laboriosam officia eos minus. Est temporibus dolores hic. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Velit ratione veritatis vitae a voluptas quibusdam vero veniam, molestiae, ducimus natus quasi alias laboriosam officia eos minus. Est temporibus dolores hic.'

  return (
    <div className="max-w-fit bg-custom-blue p-6 rounded-xl my-6">
      <div className="flex-row">
        <h2 className="font-semibold text-xl">Descrição</h2>
      </div>
      <div className="flex flex-row mx-4 mt-3">
        <p className="text-justify">{descr}</p>
      </div>
    </div>
  )
}
