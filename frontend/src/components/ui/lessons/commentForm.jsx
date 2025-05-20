import { Form, useActionData } from 'react-router-dom'
import { useEffect, useRef } from 'react'
import LargeInput from '@/components/ui/inputs/largeinput'
import { Button } from '@/components/ui/button'
import { createComment } from '@/requests/courses/createComment'

export default function CommentForm({ lessonId, onCommentSubmit }) {
    const formRef = useRef(null)
    const actionData = useActionData()

    useEffect(() => {
        if (actionData?.success && formRef.current) {
            formRef.current.reset()
            if (onCommentSubmit) {
                onCommentSubmit()
            }
        }
    }, [actionData])
    return (
        <div>
            <h2 className='font-semibold text-xl'>Comentários</h2>
            <Form
                method="post"
                action={createComment}
                ref={formRef}
                className="flex-col justify-center space-y-4 mt-4"
            >
                <LargeInput
                    placeholder="Deixe seu comentário aqui"
                    id="comment"
                    name="comment"
                    maxLength={300}
                />
                <div className="flex flex-row justify-between">
                    <button className="w-1/4 bg-transparent text-white underline" type="button" onClick={() => formRef.current.reset()}>
                        Cancelar
                    </button>
                    <Button className="w-1/4 bg-custom-header-cyan text-black">
                        Comentar
                    </Button>
                </div>
                <input type="hidden" name="lessonId" id="lessonId" value={lessonId} />
            </Form>
        </div>
    )
}
