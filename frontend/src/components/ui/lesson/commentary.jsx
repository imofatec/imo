import { Button } from '../button'
import LargeInput from '../inputs/largeinput'
import { Form, useActionData } from 'react-router-dom'
import { useEffect, useRef } from 'react'
import { createComment } from '@/requests/courses/createComment'

export default function Commentary({ parentId, lessonId }) {
  const formRef = useRef(null)
  const actionData = useActionData()
  useEffect(() => {
    if (actionData?.success && formRef.current) {
      formRef.current.reset()
    }
  }, [actionData])
  return (
    <Form method="post" action={createComment} ref={formRef}>
      <div className="my-6 ml-6 ">

        <div className="flex flex-row">
          <LargeInput placeholder="Faça seu comentário" id="comment" name="comment" maxLength={300} className='min-h-16 max-h-16 bg-transparent border-transparent border-b-white rounded-none' />
        </div>

        <input type="hidden" name="parentId" id='parentId' value={parentId} />

        <input type="hidden" name="lessonId" id="lessonId" value={lessonId} />

        <div className="flex flex-row mt-6 justify-end">


          <div className="flex flex-col">
            <button className="bg-custom-dark-blue mx-6 mt-1 bg-transparent text-white underline" type="button" onClick={() => formRef.current.reset()}>Cancelar</button>
          </div>

          <div className="flex flex-col">
            <Button className="bg-custom-header-cyan text-black">Comentar</Button>
          </div>

        </div>
      </div>
    </Form>
  )
}
