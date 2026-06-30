import { useState } from 'react'
import { useForm, Controller } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import { z } from 'zod'
import styled from 'styled-components'
import { Input, ErrorText, Field } from '@/components/Input'
import { Label } from '@/components/Label'
import { Select } from '@/components/Select'
import { Button } from '@/components/Button'
import { Separator } from '@/components/Separator'
import { toast } from '@/components/Toast'
import type { X834ConfigRequest, X834ConfigResponse } from '../hooks/useX834Config'

const ELEMENT_SEPARATOR_OPTIONS = [
  { value: 'ASTERISK', label: 'ASTERISK  ( * )' },
  { value: 'CARET', label: 'CARET  ( ^ )' },
  { value: 'PIPE', label: 'PIPE  ( | )' },
]

const configSchema = z.object({
  senderID: z.string().min(1, 'Required'),
  receiverID: z.string().min(1, 'Required'),
  elementSeparator: z.string().min(1, 'Required'),
  memberIdQualifier: z.string().min(1, 'Required'),
  policyNumber: z.string().min(1, 'Required'),
  referenceIdentification: z.string().min(1, 'Required'),
  masterPolicyNumber: z.string().min(1, 'Required'),
  planSponsorName: z.string().min(1, 'Required'),
  payerName: z.string().min(1, 'Required'),
  payerIdentification: z.string().min(1, 'Required'),
})

type ConfigFormValues = z.infer<typeof configSchema>

interface X834ConfigFormProps {
  config: X834ConfigResponse | null
  canEdit: boolean
  onSave: (data: X834ConfigRequest) => Promise<void>
}

const SectionTitle = styled.h3`
  font-size: ${({ theme }) => theme.fontSize.sm};
  font-weight: ${({ theme }) => theme.fontWeight.semibold};
  color: ${({ theme }) => theme.colors.gray[500]};
  text-transform: uppercase;
  letter-spacing: 0.05em;
  margin: 0 0 ${({ theme }) => theme.spacing[3]};
`

const FormGrid = styled.div`
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: ${({ theme }) => theme.spacing[4]};

  @media (max-width: 640px) {
    grid-template-columns: 1fr;
  }
`

const FullWidth = styled.div`
  grid-column: 1 / -1;
`

const Actions = styled.div`
  display: flex;
  gap: ${({ theme }) => theme.spacing[3]};
  padding-top: ${({ theme }) => theme.spacing[2]};
`

const ReadOnlyGrid = styled.dl`
  display: grid;
  grid-template-columns: max-content 1fr;
  gap: ${({ theme }) => `${theme.spacing[2]} ${theme.spacing[4]}`};
  margin: 0;
`

const ReadOnlyLabel = styled.dt`
  font-size: ${({ theme }) => theme.fontSize.sm};
  font-weight: ${({ theme }) => theme.fontWeight.medium};
  color: ${({ theme }) => theme.colors.gray[500]};
`

const ReadOnlyValue = styled.dd`
  font-size: ${({ theme }) => theme.fontSize.sm};
  color: ${({ theme }) => theme.colors.gray[900]};
  margin: 0;
`

const HeaderRow = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: ${({ theme }) => theme.spacing[4]};
`

const SectionLabel = styled.p`
  font-size: ${({ theme }) => theme.fontSize.xs};
  font-weight: ${({ theme }) => theme.fontWeight.semibold};
  color: ${({ theme }) => theme.colors.gray[400]};
  text-transform: uppercase;
  letter-spacing: 0.05em;
  margin: 0 0 ${({ theme }) => theme.spacing[3]};
`

function ReadOnlyConfig({ config }: { config: X834ConfigResponse }) {
  return (
    <>
      <SectionLabel>EDI Envelope</SectionLabel>
      <ReadOnlyGrid>
        <ReadOnlyLabel>Sender ID</ReadOnlyLabel>
        <ReadOnlyValue>{config.senderID}</ReadOnlyValue>
        <ReadOnlyLabel>Receiver ID</ReadOnlyLabel>
        <ReadOnlyValue>{config.receiverID}</ReadOnlyValue>
        <ReadOnlyLabel>Element Separator</ReadOnlyLabel>
        <ReadOnlyValue>{config.elementSeparator}</ReadOnlyValue>
        <ReadOnlyLabel>Member ID Qualifier</ReadOnlyLabel>
        <ReadOnlyValue>{config.memberIdQualifier}</ReadOnlyValue>
      </ReadOnlyGrid>
      <Separator />
      <SectionLabel>Plan &amp; Payer</SectionLabel>
      <ReadOnlyGrid>
        <ReadOnlyLabel>Policy Number</ReadOnlyLabel>
        <ReadOnlyValue>{config.policyNumber}</ReadOnlyValue>
        <ReadOnlyLabel>Reference ID</ReadOnlyLabel>
        <ReadOnlyValue>{config.referenceIdentification}</ReadOnlyValue>
        <ReadOnlyLabel>Master Policy Number</ReadOnlyLabel>
        <ReadOnlyValue>{config.masterPolicyNumber}</ReadOnlyValue>
        <ReadOnlyLabel>Plan Sponsor</ReadOnlyLabel>
        <ReadOnlyValue>{config.planSponsorName}</ReadOnlyValue>
        <ReadOnlyLabel>Payer Name</ReadOnlyLabel>
        <ReadOnlyValue>{config.payerName}</ReadOnlyValue>
        <ReadOnlyLabel>Payer ID</ReadOnlyLabel>
        <ReadOnlyValue>{config.payerIdentification}</ReadOnlyValue>
      </ReadOnlyGrid>
    </>
  )
}

function EditForm({
  defaultValues,
  onSave,
  onCancel,
}: {
  defaultValues?: Partial<ConfigFormValues>
  onSave: (data: X834ConfigRequest) => Promise<void>
  onCancel: () => void
}) {
  const {
    register,
    handleSubmit,
    control,
    setError,
    formState: { errors, isSubmitting },
  } = useForm<ConfigFormValues>({
    resolver: zodResolver(configSchema),
    defaultValues: defaultValues ?? {},
  })

  const onSubmit = async (values: ConfigFormValues) => {
    try {
      await onSave(values)
      toast.success('Configuration saved')
    } catch {
      setError('root', { message: 'Failed to save. Please try again.' })
    }
  }

  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <SectionTitle>EDI Envelope</SectionTitle>
      <FormGrid>
        <Field>
          <Label htmlFor="senderID">Sender ID</Label>
          <Input id="senderID" $hasError={!!errors.senderID} {...register('senderID')} />
          {errors.senderID && <ErrorText>{errors.senderID.message}</ErrorText>}
        </Field>
        <Field>
          <Label htmlFor="receiverID">Receiver ID</Label>
          <Input id="receiverID" $hasError={!!errors.receiverID} {...register('receiverID')} />
          {errors.receiverID && <ErrorText>{errors.receiverID.message}</ErrorText>}
        </Field>
        <Field>
          <Label>Element Separator</Label>
          <Controller
            name="elementSeparator"
            control={control}
            render={({ field }) => (
              <Select
                value={field.value}
                onValueChange={field.onChange}
                options={ELEMENT_SEPARATOR_OPTIONS}
                placeholder="Select…"
                $hasError={!!errors.elementSeparator}
              />
            )}
          />
          {errors.elementSeparator && <ErrorText>{errors.elementSeparator.message}</ErrorText>}
        </Field>
        <Field>
          <Label htmlFor="memberIdQualifier">Member ID Qualifier</Label>
          <Input
            id="memberIdQualifier"
            $hasError={!!errors.memberIdQualifier}
            {...register('memberIdQualifier')}
          />
          {errors.memberIdQualifier && <ErrorText>{errors.memberIdQualifier.message}</ErrorText>}
        </Field>
      </FormGrid>

      <Separator />

      <SectionTitle>Plan &amp; Payer</SectionTitle>
      <FormGrid>
        <Field>
          <Label htmlFor="policyNumber">Policy Number</Label>
          <Input id="policyNumber" $hasError={!!errors.policyNumber} {...register('policyNumber')} />
          {errors.policyNumber && <ErrorText>{errors.policyNumber.message}</ErrorText>}
        </Field>
        <Field>
          <Label htmlFor="referenceIdentification">Reference Identification</Label>
          <Input
            id="referenceIdentification"
            $hasError={!!errors.referenceIdentification}
            {...register('referenceIdentification')}
          />
          {errors.referenceIdentification && (
            <ErrorText>{errors.referenceIdentification.message}</ErrorText>
          )}
        </Field>
        <Field>
          <Label htmlFor="masterPolicyNumber">Master Policy Number</Label>
          <Input
            id="masterPolicyNumber"
            $hasError={!!errors.masterPolicyNumber}
            {...register('masterPolicyNumber')}
          />
          {errors.masterPolicyNumber && <ErrorText>{errors.masterPolicyNumber.message}</ErrorText>}
        </Field>
        <Field>
          <Label htmlFor="planSponsorName">Plan Sponsor Name</Label>
          <Input
            id="planSponsorName"
            $hasError={!!errors.planSponsorName}
            {...register('planSponsorName')}
          />
          {errors.planSponsorName && <ErrorText>{errors.planSponsorName.message}</ErrorText>}
        </Field>
        <Field>
          <Label htmlFor="payerName">Payer Name</Label>
          <Input id="payerName" $hasError={!!errors.payerName} {...register('payerName')} />
          {errors.payerName && <ErrorText>{errors.payerName.message}</ErrorText>}
        </Field>
        <Field>
          <Label htmlFor="payerIdentification">Payer Identification</Label>
          <Input
            id="payerIdentification"
            $hasError={!!errors.payerIdentification}
            {...register('payerIdentification')}
          />
          {errors.payerIdentification && (
            <ErrorText>{errors.payerIdentification.message}</ErrorText>
          )}
        </Field>
        {errors.root && (
          <FullWidth>
            <ErrorText>{errors.root.message}</ErrorText>
          </FullWidth>
        )}
      </FormGrid>

      <Actions>
        <Button type="submit" disabled={isSubmitting}>
          {isSubmitting ? 'Saving…' : 'Save Configuration'}
        </Button>
        <Button type="button" $variant="secondary" onClick={onCancel}>
          Cancel
        </Button>
      </Actions>
    </form>
  )
}

export function X834ConfigForm({ config, canEdit, onSave }: X834ConfigFormProps) {
  const [isEditing, setIsEditing] = useState(false)

  if (!config) {
    return (
      <div>
        <p style={{ color: 'var(--color-gray-500)', fontSize: '0.875rem' }}>
          No X834 configuration has been set up yet.
        </p>
        {canEdit && (
          <Button style={{ marginTop: '1rem' }} onClick={() => setIsEditing(true)}>
            Add Configuration
          </Button>
        )}
        {isEditing && (
          <div style={{ marginTop: '1.5rem' }}>
            <EditForm
              onSave={async (data) => {
                await onSave(data)
                setIsEditing(false)
              }}
              onCancel={() => setIsEditing(false)}
            />
          </div>
        )}
      </div>
    )
  }

  if (isEditing) {
    const defaults: Partial<ConfigFormValues> = {
      senderID: config.senderID,
      receiverID: config.receiverID,
      elementSeparator: config.elementSeparator,
      memberIdQualifier: config.memberIdQualifier,
      policyNumber: config.policyNumber,
      referenceIdentification: config.referenceIdentification,
      masterPolicyNumber: config.masterPolicyNumber,
      planSponsorName: config.planSponsorName,
      payerName: config.payerName,
      payerIdentification: config.payerIdentification,
    }
    return (
      <EditForm
        defaultValues={defaults}
        onSave={async (data) => {
          await onSave(data)
          setIsEditing(false)
        }}
        onCancel={() => setIsEditing(false)}
      />
    )
  }

  return (
    <>
      <HeaderRow>
        <span />
        {canEdit && (
          <Button $variant="secondary" $size="sm" onClick={() => setIsEditing(true)}>
            Edit
          </Button>
        )}
      </HeaderRow>
      <ReadOnlyConfig config={config} />
    </>
  )
}
