import { useState } from 'react'

export function useLessonValidator(schema) {
    const [formDataList, setFormDataList] = useState([
        { title: '', descriptionC: '', youtubeLink: '' }
    ])
    const [fieldErrorsList, setFieldErrorsList] = useState([{}])

    const handleChange = (index, e) => {
        const { name, value } = e.target

        const pureName = name.split('-')[0] 

        const updatedLesson = {
            ...formDataList[index],
            [pureName]: value
        }

        const newFormDataList = [...formDataList]
        newFormDataList[index] = updatedLesson
        setFormDataList(newFormDataList)

        const result = schema.safeParse(updatedLesson)

        const newFieldErrorsList = [...fieldErrorsList]
        if (!result.success) {
            const errors = {}
            result.error.errors.forEach((err) => {
                errors[err.path[0]] = err.message
            })
            newFieldErrorsList[index] = errors
        } else {
            newFieldErrorsList[index] = {}
        }

        setFieldErrorsList(newFieldErrorsList)
    }
    const addLesson = () => {
        setFormDataList([...formDataList, { title: '', descriptionC: '', youtubeLink: '' }])
        setFieldErrorsList([...fieldErrorsList, {}])
    }

    const removeLesson = (index) => {
        if (formDataList.length > 1) {
            const newFormDataList = formDataList.filter((_, i) => i !== index)
            const newFieldErrorsList = fieldErrorsList.filter((_, i) => i !== index)
            setFormDataList(newFormDataList)
            setFieldErrorsList(newFieldErrorsList)
        }
    }

    return {
        formDataList,
        fieldErrorsList,
        handleChange,
        addLesson,
        removeLesson
    }
}
